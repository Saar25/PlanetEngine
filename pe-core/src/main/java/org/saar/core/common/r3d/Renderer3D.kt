package org.saar.core.common.r3d

import org.joml.Matrix4fc
import org.saar.core.renderer.AUniformProperty
import org.saar.core.renderer.AbstractRenderer
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.Renderer
import org.saar.lwjgl.opengl.shaders.InstanceRenderState
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShadersProgram
import org.saar.lwjgl.opengl.shaders.uniforms.UniformMat4Property
import org.saar.lwjgl.opengl.utils.GlUtils
import org.saar.maths.utils.Matrix4

class Renderer3D(private vararg val renderNodes3D: RenderNode3D) : AbstractRenderer(shadersProgram), Renderer {

    @AUniformProperty
    private val mvpMatrixUniform = object : UniformMat4Property.Instance<RenderNode3D>("mvpMatrix") {
        override fun getUniformValue(state: InstanceRenderState<RenderNode3D>): Matrix4fc {
            return context!!.camera.projection.matrix.mul(context!!.camera.viewMatrix, matrix)
                    .mul(state.instance.transform.transformationMatrix)
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
        init()
    }

    private var context: RenderContext? = null

    override fun onRender(context: RenderContext) {
        GlUtils.enableAlphaBlending()
        GlUtils.enableDepthTest()
        GlUtils.enableCulling()
        GlUtils.setProvokingVertexFirst()

        this.context = context

        for (renderNode3D in this.renderNodes3D) {
            val state = InstanceRenderState(renderNode3D)
            mvpMatrixUniform.loadOnInstance(state)
            renderNode3D.draw()
        }
    }

    override fun onDelete() {
        for (renderNode3D in this.renderNodes3D) {
            renderNode3D.delete()
        }
    }
}