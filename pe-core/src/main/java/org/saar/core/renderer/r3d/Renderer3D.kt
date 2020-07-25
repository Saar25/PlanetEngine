package org.saar.core.renderer.r3d

import org.saar.core.renderer.Renderer
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShadersProgram

class Renderer3D(private val renderNode3D: RenderNode3D) : Renderer {

    companion object {
        private val vertex: Shader = Shader.createVertex(
                "/shaders/basic/vertex.glsl")
        private val fragment: Shader = Shader.createFragment(
                "/shaders/basic/fragment.glsl")
        private val shadersProgram: ShadersProgram =
                ShadersProgram.create(vertex, fragment)
    }

    init {
        shadersProgram.bindAttributes("in_position", "in_colour")
    }

    override fun render() {
        shadersProgram.bind()
        renderNode3D.model.draw()
        shadersProgram.unbind()
    }

    override fun delete() {
        shadersProgram.delete()
    }
}