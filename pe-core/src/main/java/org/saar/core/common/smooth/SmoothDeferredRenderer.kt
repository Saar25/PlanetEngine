package org.saar.core.common.smooth

import org.jproperty.type.FloatProperty
import org.jproperty.type.SimpleFloatProperty
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RendererPrototype
import org.saar.core.renderer.RendererPrototypeWrapper
import org.saar.core.renderer.deferred.DeferredRenderer
import org.saar.core.renderer.shaders.ShaderProperty
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.ShaderType
import org.saar.lwjgl.opengl.shaders.uniforms.FloatUniform
import org.saar.lwjgl.opengl.shaders.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.utils.GlUtils
import org.saar.maths.utils.Matrix4

class SmoothDeferredRenderer : DeferredRenderer, RendererPrototypeWrapper<SmoothModel>(SmoothRendererPrototype())

private class SmoothRendererPrototype : RendererPrototype<SmoothModel> {

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

    @ShaderProperty(ShaderType.VERTEX)
    private val vertex = Shader.createVertex(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/smooth/smooth.vertex.glsl"))

    @ShaderProperty(ShaderType.FRAGMENT)
    private val fragment = Shader.createFragment(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/smooth/smooth.dfragment.glsl"))

    override fun vertexAttributes() = arrayOf(
        "in_position", "in_normal", "in_colour", "in_target")

    override fun onRenderCycle(context: RenderContext) {
        GlUtils.setCullFace(context.hints.cullFace)
        GlUtils.enableAlphaBlending()
        GlUtils.enableDepthTest()
        GlUtils.setProvokingVertexFirst()
    }

    override fun onInstanceDraw(context: RenderContext, model: SmoothModel) {
        val v = context.camera.viewMatrix
        val p = context.camera.projection.matrix
        val m = model.transform.transformationMatrix

        this.mvpMatrixUniform.value = p.mul(v, Matrix4.create()).mul(m)
    }
}