package org.saar.core.common.portal

import org.saar.core.renderer.Renderer
import org.saar.core.renderer.ShadersLink
import org.saar.core.renderer.ShadersUniformsLoader
import org.saar.core.renderer.forward.ForwardRenderContext
import org.saar.core.renderer.init
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.ShadersProgram
import org.saar.lwjgl.opengl.shader.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.maths.utils.Matrix4
import org.saar.rhi.blending.BlendState
import org.saar.rhi.depthstencil.CompareOp
import org.saar.rhi.depthstencil.DepthStencilState
import org.saar.rhi.opengl.blending.toOpengl
import org.saar.rhi.opengl.depthstencil.toOpengl
import org.saar.rhi.opengl.rasterization.toOpengl
import org.saar.rhi.rasterization.CullMode
import org.saar.rhi.rasterization.RasterizationState

object PortalRenderer : Renderer<ForwardRenderContext, PortalModel> {

    private val shadersLink = PortalRendererPrototype
    private val uniformsLoader = ShadersUniformsLoader.from(this.shadersLink)

    init {
        this.shadersLink.init()
    }

    private val rasterizationState = RasterizationState(
        cullMode = CullMode.NONE,
    ).toOpengl()

    private val depthStencilState = DepthStencilState(
        depthTestEnable = true,
        depthWriteEnable = true,
        depthCompareOp = CompareOp.LESS,
    ).toOpengl()

    private val blendState = BlendState().toOpengl()

    override fun render(context: ForwardRenderContext, models: Iterable<PortalModel>) {
        this.shadersLink.shadersProgram.bind()

        this.rasterizationState.set()
        this.depthStencilState.set()
        this.blendState.set()

        val v = context.camera.viewMatrix
        val p = context.camera.projection.matrix
        val vp = p.mul(v, Matrix4.create())

        models.forEach { model ->
            val m = model.transform.transformationMatrix

            this.shadersLink.mvpMatrixUniform.value = vp.mul(m, Matrix4.temp)

            this.shadersLink.textureUniform.value = model.viewTexture

            this.uniformsLoader.load()

            model.draw()
        }
    }

    override fun delete() = this.shadersLink.shadersProgram.delete()

    private object PortalRendererPrototype : ShadersLink {

        @UniformProperty
        val mvpMatrixUniform = Mat4UniformValue("u_mvpMatrix")

        @UniformProperty
        val textureUniform = TextureUniformValue("u_texture", 0)

        override val vertexAttributes = arrayOf("in_position")

        override val shadersProgram: ShadersProgram = ShadersProgram.create(
            Shader.createVertex(GlslVersion.V400, ShaderCode.loadSource("/shaders/portal/portal.vertex.glsl")),
            Shader.createFragment(GlslVersion.V400, ShaderCode.loadSource("/shaders/portal/portal.fragment.glsl"))
        )
    }
}