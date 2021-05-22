package org.saar.core.common.obj

import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RendererPrototype
import org.saar.core.renderer.RendererPrototypeWrapper
import org.saar.core.renderer.shaders.ShaderProperty
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.ShaderType
import org.saar.lwjgl.opengl.shaders.uniforms.FloatUniform
import org.saar.lwjgl.opengl.shaders.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shaders.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.utils.GlUtils
import org.saar.maths.utils.Matrix4

object ObjDeferredRenderer : RendererPrototypeWrapper<ObjModel>(ObjDeferredRendererPrototype())

private val matrix = Matrix4.create()

private class ObjDeferredRendererPrototype : RendererPrototype<ObjModel> {

    @UniformProperty
    private val viewProjectionUniform = Mat4UniformValue("u_viewProjectionMatrix")

    @UniformProperty
    private val textureUniform = TextureUniformValue("u_texture", 0)

    @UniformProperty
    private val transformUniform = Mat4UniformValue("u_transformationMatrix")

    @UniformProperty
    private val specularUniform = object : FloatUniform() {
        override fun getName() = "u_specular"

        override fun getUniformValue() = 2.5f
    }

    @UniformProperty
    private val normalMatrixUniform = Mat4UniformValue("u_normalMatrix")

    @ShaderProperty(ShaderType.VERTEX)
    private val vertex = Shader.createVertex(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/obj/obj.vertex.glsl"))

    @ShaderProperty(ShaderType.FRAGMENT)
    private val fragment = Shader.createFragment(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/obj/obj.dfragment.glsl"))

    override fun vertexAttributes() = arrayOf(
        "in_position", "in_uvCoord", "in_normal")

    override fun fragmentOutputs() = arrayOf(
        "f_colour", "f_normal")

    override fun onRenderCycle(context: RenderContext) {
        GlUtils.setCullFace(context.hints.cullFace)
        GlUtils.enableAlphaBlending()
        GlUtils.enableDepthTest()

        this.normalMatrixUniform.value = context.camera.viewMatrix.invert(matrix).transpose()
    }

    override fun onInstanceDraw(context: RenderContext, model: ObjModel) {
        val v = context.camera.viewMatrix
        val p = context.camera.projection.matrix
        this.viewProjectionUniform.value = p.mul(v, Matrix4.create())

        this.transformUniform.value = model.transform.transformationMatrix
        this.textureUniform.value = model.texture
    }
}