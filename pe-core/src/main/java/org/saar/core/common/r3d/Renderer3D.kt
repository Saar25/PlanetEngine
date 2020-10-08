package org.saar.core.common.r3d

import org.saar.core.renderer.*
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShadersProgram
import org.saar.lwjgl.opengl.shaders.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.utils.GlUtils
import org.saar.maths.utils.Matrix4

class Renderer3D(private vararg val renderNodes3D: RenderNode3D) : AbstractRenderer(shadersProgram), Renderer {

    @UniformProperty
    private val mvpMatrixUniform = Mat4UniformValue("mvpMatrix")

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
        init()
    }

    override fun onRender(context: RenderContext) {
        GlUtils.setCullFace(context.hints.cullFace)

        GlUtils.enableAlphaBlending()
        GlUtils.enableDepthTest()
        GlUtils.setProvokingVertexFirst()

        for (renderNode3D in this.renderNodes3D) {
            val state = RenderState(renderNode3D)

            val v = context.camera.viewMatrix
            val p = context.camera.projection.matrix
            val m = state.instance.transform.transformationMatrix

            this.mvpMatrixUniform.value = p.mul(v, matrix).mul(m)
            this.mvpMatrixUniform.load()

            renderNode3D.draw()
        }
    }

    override fun onDelete() {
        for (renderNode3D in this.renderNodes3D) {
            renderNode3D.delete()
        }
    }
}