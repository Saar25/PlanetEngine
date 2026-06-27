package org.saar.core.common.renderpass

import org.joml.Vector2i
import org.saar.core.camera.ICamera
import org.saar.core.light.DirectionalLight
import org.saar.core.light.DirectionalLightUniform
import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.*
import org.saar.core.renderer.state.*
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.core.screen.Screen
import org.saar.core.screen.buildScreen
import org.saar.lwjgl.opengl.blend.BlendState
import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.cullface.CullFaceState
import org.saar.lwjgl.opengl.depth.DepthState
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.ShadersProgram
import org.saar.lwjgl.opengl.shader.uniforms.IntUniformValue
import org.saar.lwjgl.opengl.shader.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.shader.uniforms.Vec2iUniformValue
import org.saar.lwjgl.opengl.stencil.StencilState
import org.saar.lwjgl.opengl.texture.MutableTexture2D
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D
import org.saar.maths.utils.Matrix4
import org.saar.maths.utils.Vector4

fun RenderGraph.Builder.shadowsPass(input: ShadowsRenderPass.Input.() -> Unit): ShadowsRenderPass.Output {
    val outputAlbedo = MutableTexture2D.create()
    val screen = buildScreen(width, height) {
        colorAttachment(outputAlbedo, InternalFormat.RGBA16F)
    }

    shadowsPass(screen, input)

    return ShadowsRenderPass.Output(screen, outputAlbedo)
}

fun RenderGraph.Builder.shadowsPass(screen: Screen, input: ShadowsRenderPass.Input.() -> Unit) {
    val input = ShadowsRenderPass.Input().apply(input)
    addPass(ShadowsRenderPass(screen, input))
}

@JvmName("create")
fun ShadowsRenderPass(
    albedoBuffer: ReadOnlyTexture2D,
    normalSpecularBuffer: ReadOnlyTexture2D,
    depthBuffer: ReadOnlyTexture2D,
    shadowsCamera: ICamera,
    camera: ICamera,
    shadowMap: ReadOnlyTexture2D,
    light: DirectionalLight,
): ShadowsRenderPass {
    val input = ShadowsRenderPass.Input().apply {
        this.albedoBuffer = albedoBuffer
        this.normalSpecularBuffer = normalSpecularBuffer
        this.depthBuffer = depthBuffer
        this.shadowsCamera = shadowsCamera
        this.camera = camera
        this.shadowMap = shadowMap
        this.light = light
    }
    return ShadowsRenderPass(null, input)
}

class ShadowsRenderPass(private val screen: Screen?, private val input: Input) : RenderPass {

    class Input {
        lateinit var albedoBuffer: ReadOnlyTexture2D
        lateinit var normalSpecularBuffer: ReadOnlyTexture2D
        lateinit var depthBuffer: ReadOnlyTexture2D
        lateinit var shadowsCamera: ICamera
        lateinit var camera: ICamera
        lateinit var shadowMap: ReadOnlyTexture2D
        lateinit var light: DirectionalLight
    }

    class Output(val screen: Screen, val albedo: MutableTexture2D)

    private val shadersLink = ShadowsShadersLink
    private val uniformsLoader = ShadersUniformsLoader.from(this.shadersLink)

    override val renderState = CompositeRenderState(
        StencilTestRenderState(StencilState.DISABLED),
        DepthTestRenderState(DepthState.DISABLED),
        BlendTestRenderState(BlendState.DISABLED),
        CullFaceRenderState(CullFaceState.DISABLED),
    )

    override fun render(context: RenderContext) {
        this.screen?.setAsDraw()
        this.renderState.apply()

        this.shadersLink.shadersProgram.bind()
        this.shadersLink.shadowMatrixUniform.value =
            this.input.shadowsCamera.projection.matrix.mul(
                this.input.shadowsCamera.viewMatrix, Matrix4.temp
            )
        this.shadersLink.projectionMatrixInvUniform.value =
            this.input.camera.projection.matrix.invertPerspective(Matrix4.temp)

        val viewInv = this.input.camera.viewMatrix.invert(Matrix4.create())
        this.shadersLink.viewMatrixInvUniform.value = viewInv

        this.shadersLink.pcfRadiusUniform.value = 2
        this.shadersLink.shadowMapUniform.value = this.input.shadowMap
        this.shadersLink.shadowMapSizeUniform.value = Vector2i(this.input.shadowMap.width, this.input.shadowMap.height)

        this.shadersLink.colorTextureUniform.value = this.input.albedoBuffer
        this.shadersLink.normalSpecularTexture.value = this.input.normalSpecularBuffer
        this.shadersLink.depthTextureUniform.value = this.input.depthBuffer

        val viewInvT = viewInv.transpose()
        val vs = Vector4.of(this.input.light.direction, 0f).mul(viewInvT).also { it.w = 0f }.normalize()
        this.shadersLink.lightUniform.directionUniform.value.set(vs.x(), vs.y(), vs.z())
        this.shadersLink.lightUniform.colorUniform.value = input.light.color

        this.uniformsLoader.load()
        QuadMesh.draw()
    }

    override fun delete() = this.shadersLink.shadersProgram.delete()

    private object ShadowsShadersLink : ShadersLink {

        @UniformProperty
        val shadowMatrixUniform = Mat4UniformValue("u_shadowMatrix")

        @UniformProperty
        val projectionMatrixInvUniform = Mat4UniformValue("u_projectionMatrixInv")

        @UniformProperty
        val viewMatrixInvUniform = Mat4UniformValue("u_viewMatrixInv")

        @UniformProperty
        val pcfRadiusUniform = IntUniformValue("u_pcfRadius")

        @UniformProperty
        val lightUniform = DirectionalLightUniform("u_light")

        @UniformProperty
        val shadowMapUniform = TextureUniformValue("u_shadowMap", 0)

        @UniformProperty
        val shadowMapSizeUniform = Vec2iUniformValue("u_shadowMapSize")

        @UniformProperty
        val colorTextureUniform = TextureUniformValue("u_colorTexture", 1)

        @UniformProperty
        val normalSpecularTexture = TextureUniformValue("u_normalSpecularTexture", 2)

        @UniformProperty
        val depthTextureUniform = TextureUniformValue("u_depthTexture", 3)

        override val shadersProgram: ShadersProgram = ShadersProgram.create(
            Shader.createVertex(GlslVersion.V400, Renderers.quadVertexShaderCode),
            Shader.createFragment(
                GlslVersion.V400,
                ShaderCode.define("MAX_DIRECTIONAL_LIGHTS", "1"),
                ShaderCode.define("SHADOW_BIAS", String.format("%.8f", 0.01f)),
                ShaderCode.loadSource("/shaders/deferred/shadow/shadow.fragment.glsl")
            ),
        )
    }
}