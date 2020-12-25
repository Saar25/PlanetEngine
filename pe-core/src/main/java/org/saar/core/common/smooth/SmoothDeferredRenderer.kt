package org.saar.core.common.smooth

import org.jproperty.type.FloatProperty
import org.jproperty.type.SimpleFloatProperty
import org.saar.core.renderer.AbstractRenderer
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderState
import org.saar.core.renderer.UniformProperty
import org.saar.core.renderer.deferred.DeferredRenderer
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShadersProgram
import org.saar.lwjgl.opengl.shaders.uniforms.FloatUniform
import org.saar.lwjgl.opengl.shaders.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.utils.GlUtils
import org.saar.maths.utils.Matrix4

class SmoothDeferredRenderer(private vararg val models: SmoothModel)
    : AbstractRenderer(shadersProgram), DeferredRenderer {

    val targetScalar: FloatProperty = SimpleFloatProperty(.5f).also {
        it.addListener { _ -> it.value.coerceIn(0.0f, 1.0f) }
    }

    @UniformProperty
    private val mvpMatrixUniform = Mat4UniformValue("u_mvpMatrix")

    @UniformProperty
    private val targetScalarUniform = object : FloatUniform() {
        override fun getName(): String = "u_targetScalar"

        override fun getUniformValue(): Float = targetScalar.get()
    }

    companion object {
        private val matrix = Matrix4.create()

        private val vertex: Shader = Shader.createVertex(
                "/shaders/smooth/smooth.vertex.glsl")
        private val fragment: Shader = Shader.createFragment(
                "/shaders/smooth/smooth.dfragment.glsl")
        private val shadersProgram: ShadersProgram =
                ShadersProgram.create(vertex, fragment)
    }

    init {
        shadersProgram.bindAttributes("in_position", "in_normal", "in_colour", "in_target")
        init()
    }

    override fun onRender(context: RenderContext) {
        GlUtils.setCullFace(context.hints.cullFace)

        GlUtils.enableAlphaBlending()
        GlUtils.enableDepthTest()
        GlUtils.setProvokingVertexFirst()

        this.targetScalarUniform.load()

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