package org.saar.core.common.flatreflected

import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.Renderer
import org.saar.core.renderer.RendererPrototype
import org.saar.core.renderer.RendererPrototypeWrapper
import org.saar.core.renderer.shaders.ShaderProperty
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.blend.BlendTest
import org.saar.lwjgl.opengl.depth.DepthTest
import org.saar.lwjgl.opengl.provokingvertex.ProvokingVertex
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.shader.uniforms.Vec3UniformValue
import org.saar.maths.utils.Matrix4

object FlatReflectedRenderer : Renderer, RendererPrototypeWrapper<FlatReflectedModel>(FlatReflectedRendererPrototype())

private class FlatReflectedRendererPrototype() : RendererPrototype<FlatReflectedModel> {

    @UniformProperty
    private val reflectionMapUniform = TextureUniformValue("u_reflectionMap", 1)

    @UniformProperty
    private val mvpMatrixUniform = Mat4UniformValue("u_mvpMatrix")

    @UniformProperty
    private val normalUniform = Vec3UniformValue("u_normal")

    @ShaderProperty
    private val vertex: Shader = Shader.createVertex(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/flat-reflected/flat-reflected.vertex.glsl"))

    @ShaderProperty
    private val fragment: Shader = Shader.createFragment(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/flat-reflected/flat-reflected.fragment.glsl"))

    override fun vertexAttributes() = arrayOf(
        "in_position", "in_normal")

    override fun onRenderCycle(context: RenderContext) {
        ProvokingVertex.setFirst();
        BlendTest.disable()
        DepthTest.enable()
    }

    override fun onInstanceDraw(context: RenderContext, model: FlatReflectedModel) {
        val v = context.camera.viewMatrix
        val p = context.camera.projection.matrix
        val m = model.transform.transformationMatrix

        this.mvpMatrixUniform.value = p.mul(v, Matrix4.temp).mul(m)
        this.reflectionMapUniform.value = model.reflectionMap
        this.normalUniform.value = model.normal
    }

    override fun doInstanceDraw(context: RenderContext, model: FlatReflectedModel) = model.draw()
}