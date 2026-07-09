package org.saar.core.common.normalmap

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

object NormalMappedRenderer : Renderer<ForwardRenderContext, NormalMappedModel> {

    private val shadersLink = NormalMappedRendererPrototype
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

    override fun render(context: ForwardRenderContext, models: Iterable<NormalMappedModel>) {
        this.shadersLink.shadersProgram.bind()

        this.depthStencilState.set()
        this.blendState.set()

        models.forEach { model ->
            val v = context.camera.viewMatrix
            val p = context.camera.projection.matrix
            this.shadersLink.viewProjectionUniform.value = p.mul(v, Matrix4.temp)

            this.shadersLink.transformationUniform.value.set(model.transform.transformationMatrix)
            this.shadersLink.textureUniform.value = model.texture
            this.shadersLink.normalMapUniform.value = model.normalMap

            this.uniformsLoader.load()

            model.draw()
        }
    }

    override fun delete() = this.shadersLink.shadersProgram.delete()

    private object NormalMappedRendererPrototype : ShadersLink {

        @UniformProperty
        val viewProjectionUniform = Mat4UniformValue("u_viewProjection")

        @UniformProperty
        val transformationUniform = Mat4UniformValue("u_transformation")

        @UniformProperty
        val textureUniform = TextureUniformValue("u_texture", 0)

        @UniformProperty
        val normalMapUniform = TextureUniformValue("u_normalMap", 1)

        override val vertexAttributes = arrayOf("in_position", "in_uvCoord", "in_normal", "in_tangent", "in_biTangent")

        override val shadersProgram = ShaderProgram(
            ShaderStage(
                type = ShaderStageType.VERTEX,
                module = ShaderModule.fromString(GlslVersion.V400.toString() + "\n" + ShaderModuleLoader.loadSource("/shaders/normal-map/normal-map.vertex.glsl"))
            ),
            ShaderStage(
                type = ShaderStageType.FRAGMENT, module = ShaderModule.fromString(
                    GlslVersion.V400.toString() + "\n" +
                            ShaderModuleLoader.loadSource("/shaders/normal-map/normal-map.fragment.glsl")
                )
            ),
        ).toOpengl()
    }
}