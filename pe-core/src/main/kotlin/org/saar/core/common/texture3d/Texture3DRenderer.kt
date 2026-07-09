package org.saar.core.common.texture3d

import org.saar.core.renderer.Renderer
import org.saar.core.renderer.ShadersLink
import org.saar.core.renderer.ShadersUniformsLoader
import org.saar.core.renderer.forward.ForwardRenderContext
import org.saar.core.renderer.init
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.shader.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.maths.utils.Matrix4
import org.saar.rhi.blending.BlendState
import org.saar.rhi.depthstencil.CompareOp
import org.saar.rhi.depthstencil.DepthStencilState
import org.saar.rhi.opengl.blending.toOpengl
import org.saar.rhi.opengl.depthstencil.toOpengl
import org.saar.rhi.opengl.rasterization.toOpengl
import org.saar.rhi.opengl.shader.toOpengl
import org.saar.rhi.rasterization.CullMode
import org.saar.rhi.rasterization.RasterizationState
import org.saar.rhi.shader.*

object Texture3DRenderer : Renderer<ForwardRenderContext, Texture3DModel> {

    private val shadersLink = Texture3DRendererPrototype
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

    override fun render(context: ForwardRenderContext, models: Iterable<Texture3DModel>) {
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

            this.shadersLink.textureUniform.value = model.texture

            this.uniformsLoader.load()

            model.draw()
        }
    }

    override fun delete() = this.shadersLink.shadersProgram.delete()

    private object Texture3DRendererPrototype : ShadersLink {

        @UniformProperty
        val mvpMatrixUniform = Mat4UniformValue("u_mvpMatrix")

        @UniformProperty
        val textureUniform = TextureUniformValue("u_texture", 0)

        override val vertexAttributes = arrayOf("in_position", "in_color", "in_transformation")

        override val shadersProgram = ShaderProgram(
            ShaderStage(
                type = ShaderStageType.VERTEX,
                module = ShaderModule.fromString(GlslVersion.V400.toString() + "\n" + ShaderModuleLoader.loadSource("/shaders/texture3d/texture3d.vertex.glsl"))
            ),
            ShaderStage(
                type = ShaderStageType.FRAGMENT,
                module = ShaderModule.fromString(GlslVersion.V400.toString() + "\n" + ShaderModuleLoader.loadSource("/shaders/texture3d/texture3d.fragment.glsl"))
            ),
        ).toOpengl()
    }
}