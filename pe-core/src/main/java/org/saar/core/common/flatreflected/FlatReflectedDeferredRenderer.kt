package org.saar.core.common.flatreflected

import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RendererPrototype
import org.saar.core.renderer.RendererPrototypeWrapper
import org.saar.core.renderer.shaders.ShaderProperty
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.blend.BlendTest
import org.saar.lwjgl.opengl.depth.DepthTest
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.uniforms.FloatUniform
import org.saar.lwjgl.opengl.shaders.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shaders.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.shaders.uniforms.Vec3UniformValue
import org.saar.lwjgl.opengl.utils.GlCullFace
import org.saar.lwjgl.opengl.utils.GlUtils
import org.saar.maths.utils.Matrix4

object FlatReflectedDeferredRenderer :
    RendererPrototypeWrapper<FlatReflectedModel>(FlatReflectedDeferredRendererPrototype())

private class FlatReflectedDeferredRendererPrototype : RendererPrototype<FlatReflectedModel> {

    @UniformProperty
    private val reflectionMapUniform = TextureUniformValue("u_reflectionMap", 0)

    @UniformProperty
    private val mvpMatrixUniform = Mat4UniformValue("u_mvpMatrix")

    @UniformProperty
    private val normalUniform = Vec3UniformValue("u_normal")

    @UniformProperty
    private val specularUniform = object : FloatUniform() {
        override val name = "u_specular"

        override val value = 1f
    }

    @UniformProperty
    private val normalMatrixUniform = Mat4UniformValue("u_normalMatrix")

    @ShaderProperty
    private val vertex = Shader.createVertex(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/flat-reflected/flat-reflected.vertex.glsl"))

    @ShaderProperty
    private val fragment = Shader.createFragment(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/flat-reflected/flat-reflected.dfragment.glsl"))

    override fun vertexAttributes() = arrayOf(
        "in_position", "in_normal")

    override fun onRenderCycle(context: RenderContext) {
        GlUtils.setCullFace(GlCullFace.NONE)
        GlUtils.setProvokingVertexFirst()
        BlendTest.disable()
        DepthTest.enable()

        this.normalMatrixUniform.value = context.camera.viewMatrix.invert(Matrix4.temp).transpose()
    }

    override fun onInstanceDraw(context: RenderContext, model: FlatReflectedModel) {
        val v = context.camera.viewMatrix
        val p = context.camera.projection.matrix
        val m = model.transform.transformationMatrix

        this.mvpMatrixUniform.value = p.mul(v, Matrix4.temp).mul(m)

        this.normalUniform.value = model.normal
        this.reflectionMapUniform.value = model.reflectionMap
    }

    override fun doInstanceDraw(context: RenderContext, model: FlatReflectedModel) = model.draw()
}