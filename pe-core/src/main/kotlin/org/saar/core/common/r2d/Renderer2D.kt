package org.saar.core.common.r2d

import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.Renderer
import org.saar.core.renderer.ShadersLink
import org.saar.core.renderer.init
import org.saar.rhi.opengl.shader.toOpengl
import org.saar.rhi.shader.*

object Renderer2D : Renderer<RenderContext, Model2D> {

    private val shadersLink = RendererPrototype2D

    init {
        this.shadersLink.init()
    }

    override fun render(context: RenderContext, models: Iterable<Model2D>) {
        this.shadersLink.shadersProgram.bind()

        models.forEach(Model2D::draw)
    }

    override fun delete() = this.shadersLink.shadersProgram.delete()

    private object RendererPrototype2D : ShadersLink {

        override val vertexAttributes = arrayOf("in_position", "in_color")

        override val fragmentOutputs = arrayOf("f_color")

        override val shadersProgram = ShaderProgram(
            ShaderStage(
                type = ShaderStageType.VERTEX,
                module = ShaderModule.fromString(GlslVersion.V400.toString() + "\n" + ShaderModuleLoader.loadSource("/shaders/r2d/r2d.vertex.glsl"))
            ),
            ShaderStage(
                type = ShaderStageType.FRAGMENT,
                module = ShaderModule.fromString(GlslVersion.V400.toString() + "\n" + ShaderModuleLoader.loadSource("/shaders/r2d/r2d.fragment.glsl"))
            ),
        ).toOpengl()
    }
}