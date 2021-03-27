package org.saar.core.common.r2d

import org.saar.core.renderer.AbstractRenderer
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.shaders.ShaderProperty
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderType

class Renderer2D(private val model: Model2D) : AbstractRenderer() {

    @ShaderProperty(ShaderType.VERTEX)
    private val vertex = Shader.createVertex("/shaders/r2d/vertex.glsl")

    @ShaderProperty(ShaderType.FRAGMENT)
    private val fragment = Shader.createFragment("/shaders/r2d/fragment.glsl")

    init {
        buildShadersProgram()
        bindAttributes("in_position", "in_colour")
    }

    override fun onRender(context: RenderContext) {
        model.draw()
    }
}