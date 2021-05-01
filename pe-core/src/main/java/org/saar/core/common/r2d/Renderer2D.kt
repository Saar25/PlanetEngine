package org.saar.core.common.r2d

import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.Renderer
import org.saar.core.renderer.RendererPrototype
import org.saar.core.renderer.RendererPrototypeWrapper
import org.saar.core.renderer.shaders.ShaderProperty
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.ShaderType

class Renderer2D(private vararg val models: Model2D) : Renderer,
    RendererPrototypeWrapper<Model2D>(RendererPrototype2D()) {

    override fun render(context: RenderContext, vararg models: Model2D) {
        super.render(context, *this.models, *models)
    }

    override fun render(context: RenderContext) {
        super.render(context, *this.models)
    }

    override fun delete() {
        this.models.forEach { it.delete() }
    }
}

private class RendererPrototype2D : RendererPrototype<Model2D> {

    @ShaderProperty(ShaderType.VERTEX)
    private val vertex = Shader.createVertex(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/r2d/r2d.vertex.glsl"))

    @ShaderProperty(ShaderType.FRAGMENT)
    private val fragment = Shader.createFragment(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/r2d/r2d.fragment.glsl"))

    override fun vertexAttributes() = arrayOf("in_position", "in_colour")
}