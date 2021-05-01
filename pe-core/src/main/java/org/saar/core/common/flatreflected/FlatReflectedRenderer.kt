package org.saar.core.common.flatreflected

import org.saar.core.renderer.*
import org.saar.core.renderer.shaders.ShaderProperty
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.shaders.*
import org.saar.lwjgl.opengl.shaders.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shaders.uniforms.TextureUniform
import org.saar.lwjgl.opengl.shaders.uniforms.Vec3UniformValue
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture
import org.saar.lwjgl.opengl.utils.GlCullFace
import org.saar.lwjgl.opengl.utils.GlUtils
import org.saar.maths.utils.Matrix4

class FlatReflectedRenderer(private vararg val models: FlatReflectedModel, reflectionMap: ReadOnlyTexture) : Renderer,
    RendererPrototypeWrapper<FlatReflectedModel>(FlatReflectedRendererPrototype(reflectionMap)) {

    override fun render(context: RenderContext, vararg models: FlatReflectedModel) {
        render(context, *this.models, *models)
    }

    override fun render(context: RenderContext) {
        render(context, *this.models)
    }

    override fun delete() {
        this.models.forEach { it.delete() }
    }
}

private class FlatReflectedRendererPrototype(private val reflectionMap: ReadOnlyTexture) :
    RendererPrototype<FlatReflectedModel> {

    @UniformProperty
    private val reflectionMapUniform = object : TextureUniform() {
        override fun getUnit(): Int = 1

        override fun getName(): String = "u_reflectionMap"

        override fun getUniformValue(): ReadOnlyTexture {
            return this@FlatReflectedRendererPrototype.reflectionMap
        }
    }

    @UniformProperty
    private val mvpMatrixUniform = Mat4UniformValue("u_mvpMatrix")

    @UniformProperty
    private val normalUniform = Vec3UniformValue("u_normal")

    @ShaderProperty(ShaderType.VERTEX)
    private val vertex: Shader = Shader.createVertex(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/flat-reflected/flat-reflected.vertex.glsl"))

    @ShaderProperty(ShaderType.FRAGMENT)
    private val fragment: Shader = Shader.createFragment(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/flat-reflected/flat-reflected.fragment.glsl"))

    override fun vertexAttributes() = arrayOf(
        "in_position", "in_normal")

    override fun onRenderCycle(context: RenderContext) {
        GlUtils.setCullFace(GlCullFace.NONE)
        GlUtils.enableAlphaBlending()
        GlUtils.enableDepthTest()
        GlUtils.setProvokingVertexFirst()
    }

    override fun onInstanceDraw(context: RenderContext, model: FlatReflectedModel) {
        val v = context.camera.viewMatrix
        val p = context.camera.projection.matrix
        val m = model.transform.transformationMatrix

        this.mvpMatrixUniform.value = p.mul(v, Matrix4.create()).mul(m)
    }
}