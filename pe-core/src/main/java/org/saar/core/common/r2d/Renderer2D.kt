package org.saar.core.common.r2d

import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.Renderer
import org.saar.core.renderer.RendererPrototype
import org.saar.core.renderer.RendererPrototypeWrapper
import org.saar.core.renderer.shaders.ShaderProperty
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode

object Renderer2D : Renderer, RendererPrototypeWrapper<Model2D>(RendererPrototype2D())

private class RendererPrototype2D : RendererPrototype<Model2D> {

    @ShaderProperty
    private val vertex = Shader.createVertex(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/r2d/r2d.vertex.glsl"))

    @ShaderProperty
    private val fragment = Shader.createFragment(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/r2d/r2d.fragment.glsl"))

    override fun vertexAttributes() = arrayOf("in_position", "in_colour")

    override fun doInstanceDraw(context: RenderContext, model: Model2D) = model.draw()
}