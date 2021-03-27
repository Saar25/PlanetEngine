package org.saar.core.common.r3d

import org.saar.core.renderer.AbstractRenderer
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderState
import org.saar.core.renderer.Renderer
import org.saar.core.renderer.shaders.ShaderProperty
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderType
import org.saar.lwjgl.opengl.shaders.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.utils.GlUtils
import org.saar.maths.utils.Matrix4

class Renderer3D(private vararg val models: Model3D) : AbstractRenderer(), Renderer {

    @UniformProperty
    private val mvpMatrixUniform = Mat4UniformValue("mvpMatrix")

    @ShaderProperty(ShaderType.VERTEX)
    private val vertex = Shader.createVertex("/shaders/r3d/vertex.glsl")

    @ShaderProperty(ShaderType.FRAGMENT)
    private val fragment = Shader.createFragment("/shaders/r3d/fragment.glsl")

    companion object {
        private val matrix = Matrix4.create()
    }

    init {
        buildShadersProgram()
        bindAttributes("in_position",
            "in_colour", "in_transformation")
        init()
    }

    override fun preRender(context: RenderContext) {
        GlUtils.setCullFace(context.hints.cullFace)
        GlUtils.enableAlphaBlending()
        GlUtils.enableDepthTest()
        GlUtils.setProvokingVertexFirst()
    }

    override fun onRender(context: RenderContext) {
        for (model in this.models) {
            val state = RenderState(model)

            val v = context.camera.viewMatrix
            val p = context.camera.projection.matrix
            val m = state.instance.transform.transformationMatrix

            this.mvpMatrixUniform.value = p.mul(v, matrix).mul(m)
            this.mvpMatrixUniform.load()

            model.draw()
        }
    }

    override fun onDelete() {
        for (model in this.models) {
            model.delete()
        }
    }
}