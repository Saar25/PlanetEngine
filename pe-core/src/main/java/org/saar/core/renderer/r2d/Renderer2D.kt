package org.saar.core.renderer.r2d

import org.saar.core.renderer.Renderer
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShadersProgram

class Renderer2D(private val renderNode: RenderNode2D) : Renderer {

    companion object {
        private val vertex: Shader = Shader.createVertex(
                "/shaders/r2d/vertex.glsl")
        private val fragment: Shader = Shader.createFragment(
                "/shaders/r2d/fragment.glsl")
        private val shadersProgram: ShadersProgram =
                ShadersProgram.create(vertex, fragment)
    }

    init {
        shadersProgram.bindAttributes("in_position", "in_colour")
    }

    override fun render() {
        shadersProgram.bind()
        renderNode.model.draw()
        shadersProgram.unbind()
    }

    override fun delete() {
        shadersProgram.delete()
    }
}