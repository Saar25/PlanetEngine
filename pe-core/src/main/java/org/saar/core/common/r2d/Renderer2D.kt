package org.saar.core.common.r2d

import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.Renderer
import org.saar.core.renderer.RendererPrototype
import org.saar.core.renderer.RendererPrototypeWrapper
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.ShadersProgram

object Renderer2D : Renderer, RendererPrototypeWrapper<Model2D>(RendererPrototype2D())

private class RendererPrototype2D : RendererPrototype<Model2D> {

    override val shadersProgram: ShadersProgram = ShadersProgram.create(
        Shader.createVertex(GlslVersion.V400, ShaderCode.loadSource("/shaders/r2d/r2d.vertex.glsl")),
        Shader.createFragment(GlslVersion.V400, ShaderCode.loadSource("/shaders/r2d/r2d.fragment.glsl"))
    )

    override fun vertexAttributes() = arrayOf("in_position", "in_colour")

    override fun fragmentOutputs() = arrayOf("f_colour")

    override fun doInstanceDraw(context: RenderContext, model: Model2D) = model.draw()
}