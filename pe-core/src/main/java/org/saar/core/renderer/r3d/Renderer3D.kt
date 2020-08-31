package org.saar.core.renderer.r3d

import org.joml.Matrix4fc
import org.saar.core.camera.ICamera
import org.saar.core.renderer.AbstractRenderer
import org.saar.core.renderer.Renderer
import org.saar.core.renderer.annotations.StageUniformProperty
import org.saar.lwjgl.opengl.shaders.RenderState
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShadersProgram
import org.saar.lwjgl.opengl.shaders.uniforms.UniformMat4Property
import org.saar.lwjgl.opengl.utils.GlUtils
import org.saar.maths.utils.Matrix4

class Renderer3D(private val camera: ICamera, private val renderNode3D: RenderNode3D) : AbstractRenderer(shadersProgram), Renderer {

    @StageUniformProperty
    private val viewProjectionUniform = object : UniformMat4Property<Any>("viewProjectionMatrix") {
        override fun getUniformValue(state: RenderState<Any>?): Matrix4fc {
            return camera.projection.matrix.mul(camera.viewMatrix, matrix)
        }
    }

    companion object {
        private val matrix = Matrix4.create()

        private val vertex: Shader = Shader.createVertex(
                "/shaders/r3d/vertex.glsl")
        private val fragment: Shader = Shader.createFragment(
                "/shaders/r3d/fragment.glsl")
        private val shadersProgram: ShadersProgram =
                ShadersProgram.create(vertex, fragment)
    }

    init {
        shadersProgram.bindAttributes("in_position", "in_colour", "in_transformation")
        GlUtils.enableAlphaBlending()
        GlUtils.enableDepthTest()
        GlUtils.enableCulling()
        init()
    }

    override fun onRender() {
        viewProjectionUniform.load(null)
        renderNode3D.render()
    }

    override fun onDelete() {
        renderNode3D.delete()
    }
}