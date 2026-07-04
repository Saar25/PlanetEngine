package org.saar.core.common.renderpass

import org.saar.core.camera.ICamera
import org.saar.core.light.DirectionalLight
import org.saar.core.light.DirectionalLightUniform
import org.saar.core.light.PointLight
import org.saar.core.light.PointLightUniform
import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.*
import org.saar.core.renderer.state.*
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.core.screen.Screen
import org.saar.core.screen.buildScreen
import org.saar.lwjgl.opengl.blend.BlendState
import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.depth.DepthState
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.ShadersProgram
import org.saar.lwjgl.opengl.shader.uniforms.IntUniformValue
import org.saar.lwjgl.opengl.shader.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.shader.uniforms.UniformArray
import org.saar.lwjgl.opengl.stencil.StencilState
import org.saar.lwjgl.opengl.texture.MutableTexture2D
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D
import org.saar.maths.utils.Matrix4
import org.saar.maths.utils.Vector4
import org.saar.rhi.rasterization.CullMode
import org.saar.rhi.rasterization.RasterizationState
import kotlin.math.max

fun RenderGraph.Builder.lightPass(input: LightRenderPass.Input.() -> Unit): LightRenderPass.Output {
    val outputAlbedo = MutableTexture2D.create()
    val screen = buildScreen(width, height) {
        colorAttachment(outputAlbedo, InternalFormat.RGBA16F)
    }

    lightPass(screen, input)

    return LightRenderPass.Output(screen, outputAlbedo)
}

fun RenderGraph.Builder.lightPass(screen: Screen, input: LightRenderPass.Input.() -> Unit) {
    val input = LightRenderPass.Input().apply(input)
    addPass(LightRenderPass(screen, input))
}

@JvmName("create")
fun LightRenderPass(
    albedoBuffer: ReadOnlyTexture2D,
    normalSpecularBuffer: ReadOnlyTexture2D,
    depthBuffer: ReadOnlyTexture2D,
    camera: ICamera,
    pointLights: Array<PointLight> = emptyArray(),
    directionalLights: Array<DirectionalLight> = emptyArray(),
): LightRenderPass {
    val input = LightRenderPass.Input().apply {
        this.albedoBuffer = albedoBuffer
        this.normalSpecularBuffer = normalSpecularBuffer
        this.depthBuffer = depthBuffer
        this.camera = camera
        this.pointLights = pointLights
        this.directionalLights = directionalLights
    }
    return LightRenderPass(null, input)
}

class LightRenderPass(private val screen: Screen?, private val input: Input) : RenderPass {

    class Input {
        lateinit var albedoBuffer: ReadOnlyTexture2D
        lateinit var normalSpecularBuffer: ReadOnlyTexture2D
        lateinit var depthBuffer: ReadOnlyTexture2D
        lateinit var camera: ICamera
        var pointLights: Array<PointLight> = emptyArray()
        var directionalLights: Array<DirectionalLight> = emptyArray()
    }

    class Output(val screen: Screen, val albedo: MutableTexture2D)

    private val shadersLink = LightShadersLink(this.input.pointLights.size, this.input.directionalLights.size)
    private val uniformsLoader = ShadersUniformsLoader.from(this.shadersLink)

    override val renderState = CompositeRenderState(
        StencilTestRenderState(StencilState.REPLACE),
        DepthTestRenderState(DepthState.DISABLED),
        BlendTestRenderState(BlendState.DISABLED),
        RasterizationRenderState(RasterizationState(cullMode = CullMode.NONE))
    )

    override fun render(context: RenderContext) {
        this.screen?.setAsDraw()
        this.renderState.apply()

        this.shadersLink.shadersProgram.bind()
        this.shadersLink.colorTextureUniform.value = this.input.albedoBuffer
        this.shadersLink.normalSpecularTextureUniform.value = this.input.normalSpecularBuffer
        this.shadersLink.depthTextureUniform.value = this.input.depthBuffer

        this.shadersLink.projectionMatrixInvUniform.value =
            this.input.camera.projection.matrix.invertPerspective(Matrix4.temp.identity())

        val viewInvT = this.input.camera.viewMatrix.invert(Matrix4.create()).transpose()

        this.shadersLink.directionalLightsCountUniform.value = this.input.directionalLights.size
        this.input.directionalLights.forEachIndexed { index, light ->
            val vs = Vector4.of(light.direction, 0f).mul(viewInvT).also { it.w = 0f }.normalize()
            this.shadersLink.directionalLightsUniform.value[index].directionUniform.value.set(vs.x(), vs.y(), vs.z())
            this.shadersLink.directionalLightsUniform.value[index].colorUniform.value = light.color
        }

        this.shadersLink.pointLightsCountUniform.value = this.input.pointLights.size
        this.input.pointLights.forEachIndexed { index, light ->
            val vs = Vector4.of(light.position, 1f).mul(this.input.camera.viewMatrix).let { it.div(it.w()) }
            this.shadersLink.pointLightsUniform.value[index].positionUniform.value.set(vs.x(), vs.y(), vs.z())
            this.shadersLink.pointLightsUniform.value[index].attenuationUniform.value.set(light.attenuation.vector3f)
            this.shadersLink.pointLightsUniform.value[index].colorUniform.value.set(light.color)
        }

        this.uniformsLoader.load()
        QuadMesh.draw()
    }


    override fun delete() = this.shadersLink.shadersProgram.delete()

    private class LightShadersLink(pointLights: Int, directionalLights: Int) : ShadersLink {

        @UniformProperty
        val colorTextureUniform = TextureUniformValue("u_colorTexture", 0)

        @UniformProperty
        val normalSpecularTextureUniform = TextureUniformValue("u_normalSpecularTexture", 1)

        @UniformProperty
        val depthTextureUniform = TextureUniformValue("u_depthTexture", 2)

        @UniformProperty
        val projectionMatrixInvUniform = Mat4UniformValue("u_projectionMatrixInv")

        @UniformProperty
        val directionalLightsCountUniform = IntUniformValue("u_directionalLightsCount")

        @UniformProperty
        val directionalLightsUniform = UniformArray("u_directionalLights", directionalLights, ::DirectionalLightUniform)

        @UniformProperty
        val pointLightsCountUniform = IntUniformValue("u_pointLightsCount")

        @UniformProperty
        val pointLightsUniform = UniformArray("u_pointLights", pointLights, ::PointLightUniform)

        override val shadersProgram: ShadersProgram = ShadersProgram.create(
            Shader.createVertex(GlslVersion.V400, Renderers.quadVertexShaderCode),
            Shader.createFragment(
                GlslVersion.V400,
                ShaderCode.define("MAX_POINT_LIGHTS", max(pointLights, 1).toString()),
                ShaderCode.define("MAX_DIRECTIONAL_LIGHTS", max(directionalLights, 1).toString()),
                ShaderCode.loadSource("/shaders/deferred/light/light.fragment.glsl")
            ),
        )
    }
}
