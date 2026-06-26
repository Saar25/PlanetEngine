package org.saar.core.common.r2d

import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.Renderer
import org.saar.core.renderer.ShadersLink
import org.saar.core.renderer.init
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.ShadersProgram

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

        override val shadersProgram: ShadersProgram = ShadersProgram.create(
            Shader.createVertex(GlslVersion.V400, ShaderCode.loadSource("/shaders/r2d/r2d.vertex.glsl")),
            Shader.createFragment(GlslVersion.V400, ShaderCode.loadSource("/shaders/r2d/r2d.fragment.glsl"))
        )
    }
}