package org.saar.core.common.renderpass

import org.joml.Vector2i
import org.saar.core.camera.ICamera
import org.saar.core.light.DirectionalLight
import org.saar.core.light.DirectionalLightUniform
import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.*
import org.saar.core.renderer.state.BlendTestRenderState
import org.saar.core.renderer.state.CompositeRenderState
import org.saar.core.renderer.state.CullFaceRenderState
import org.saar.core.renderer.state.DepthTestRenderState
import org.saar.core.renderer.state.StencilTestRenderState
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.blend.BlendState
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
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D
import org.saar.maths.utils.Matrix4
import org.saar.maths.utils.Vector4

class ShadowsRenderPass(
    private val albedoBuffer: ReadOnlyTexture2D,
    private val normalSpecularBuffer: ReadOnlyTexture2D,
    private val depthBuffer: ReadOnlyTexture2D,
    private val shadowsCamera: ICamera,
    private val camera: ICamera,
    private val shadowMap: ReadOnlyTexture2D,
    private val light: DirectionalLight
) : RenderPass {

    private val shadersLink = ShadowsShadersLink
    private val uniformsLoader = ShadersUniformsLoader.from(this.shadersLink)

    override val renderState = CompositeRenderState(
        StencilTestRenderState(StencilState.REPLACE),
        DepthTestRenderState(DepthState.DISABLED),
        BlendTestRenderState(BlendState.DISABLED),
        CullFaceRenderState(CullFaceState.DISABLED),
    )

    override fun render(context: RenderContext) {
        this.shadersLink.shadersProgram.bind()
        this.shadersLink.shadowMatrixUniform.value =
            this.shadowsCamera.projection.matrix.mul(
                this.shadowsCamera.viewMatrix, Matrix4.temp
            )
        this.shadersLink.projectionMatrixInvUniform.value =
            this.camera.projection.matrix.invertPerspective(Matrix4.temp)

        val viewInv = this.camera.viewMatrix.invert(Matrix4.create())
        this.shadersLink.viewMatrixInvUniform.value = viewInv

        this.shadersLink.pcfRadiusUniform.value = 2
        this.shadersLink.shadowMapUniform.value = this.shadowMap
        this.shadersLink.shadowMapSizeUniform.value = Vector2i(shadowMap.width, shadowMap.height)

        this.shadersLink.colourTextureUniform.value = this.albedoBuffer
        this.shadersLink.normalSpecularTexture.value = this.normalSpecularBuffer
        this.shadersLink.depthTextureUniform.value = this.depthBuffer

        val viewInvT = viewInv.transpose()
        val vs = Vector4.of(this.light.direction, 0f).mul(viewInvT).also { it.w = 0f }.normalize()
        this.shadersLink.lightUniform.directionUniform.value.set(vs.x(), vs.y(), vs.z())
        this.shadersLink.lightUniform.colourUniform.value = light.colour

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
        val colourTextureUniform = TextureUniformValue("u_colourTexture", 1)

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