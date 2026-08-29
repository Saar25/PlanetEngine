package org.saar.core.common.obj

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
import org.saar.rhi.opengl.shader.toOpengl
import org.saar.rhi.shader.*

object ObjRenderer : Renderer<ForwardRenderContext, ObjModel> {

    private val shadersLink = ObjRendererPrototype
    private val uniformsLoader = ShadersUniformsLoader.from(this.shadersLink)

    init {
        this.shadersLink.init()
    }

    private val depthStencilState = DepthStencilState(
        depthTestEnable = true,
        depthWriteEnable = true,
        depthCompareOp = CompareOp.LESS,
    ).toOpengl()

    private val blendState = BlendState().toOpengl()

    override fun render(context: ForwardRenderContext, models: Iterable<ObjModel>) {
        this.shadersLink.shadersProgram.bind()

        this.depthStencilState.set()
        this.blendState.set()

        val v = context.camera.viewMatrix
        val p = context.camera.projection.matrix
        this.shadersLink.viewProjectionUniform.value = p.mul(v, Matrix4.temp)

        models.forEach { model ->
            this.shadersLink.textureUniform.value = model.texture
            this.shadersLink.transformUniform.value.set(model.transform.transformationMatrix)

            this.uniformsLoader.load()

            model.draw()
        }
    }

    override fun delete() = this.shadersLink.shadersProgram.delete()

    private object ObjRendererPrototype : ShadersLink {

        @UniformProperty
        val viewProjectionUniform = Mat4UniformValue("u_viewProjectionMatrix")

        @UniformProperty
        val textureUniform = TextureUniformValue("u_texture", 0)

        @UniformProperty
        val transformUniform = Mat4UniformValue("u_transformationMatrix")

        override val vertexAttributes = arrayOf("in_position", "in_uvCoord", "in_normal")

        override val shadersProgram = ShaderProgram(
            ShaderStage(
                type = ShaderStageType.VERTEX,
                module = ShaderModule.fromString(GlslVersion.V400.toString() + "\n" + ShaderModuleLoader.loadSource("/shaders/obj/obj.vertex.glsl"))
            ),
            ShaderStage(
                type = ShaderStageType.FRAGMENT,
                module = ShaderModule.fromString(GlslVersion.V400.toString() + "\n" + ShaderModuleLoader.loadSource("/shaders/obj/obj.fragment.glsl"))
            ),
        ).toOpengl()
    }
}