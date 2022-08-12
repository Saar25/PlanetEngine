package org.saar.core.common.obj

import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RendererPrototype
import org.saar.core.renderer.RendererPrototypeWrapper
import org.saar.core.renderer.shaders.ShaderProperty
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.blend.BlendTest
import org.saar.lwjgl.opengl.depth.DepthTest
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.uniforms.FloatUniform
import org.saar.lwjgl.opengl.shader.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.maths.utils.Matrix4

object ObjDeferredRenderer : RendererPrototypeWrapper<ObjModel>(ObjDeferredRendererPrototype())

private class ObjDeferredRendererPrototype : RendererPrototype<ObjModel> {

    @UniformProperty
    private val viewProjectionUniform = Mat4UniformValue("u_viewProjectionMatrix")

    @UniformProperty
    private val textureUniform = TextureUniformValue("u_texture", 0)

    @UniformProperty
    private val transformUniform = Mat4UniformValue("u_transformationMatrix")

    @UniformProperty
    private val specularUniform = object : FloatUniform() {
        override val name = "u_specular"

        override val value = 2.5f
    }

    @UniformProperty
    private val normalMatrixUniform = Mat4UniformValue("u_normalMatrix")

    @ShaderProperty
    private val vertex = Shader.createVertex(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/obj/obj.vertex.glsl"))

    @ShaderProperty
    private val fragment = Shader.createFragment(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/obj/obj.dfragment.glsl"))

    override fun vertexAttributes() = arrayOf(
        "in_position", "in_uvCoord", "in_normal")

    override fun fragmentOutputs() = arrayOf(
        "f_colour", "f_normal")

    override fun onRenderCycle(context: RenderContext) {
        BlendTest.disable()
        DepthTest.enable()

        this.normalMatrixUniform.value = context.camera.viewMatrix.invert(Matrix4.temp).transpose()
    }

    override fun onInstanceDraw(context: RenderContext, model: ObjModel) {
        val v = context.camera.viewMatrix
        val p = context.camera.projection.matrix
        this.viewProjectionUniform.value = p.mul(v, Matrix4.temp)

        this.transformUniform.value.set(model.transform.transformationMatrix)
        this.textureUniform.value = model.texture
    }

    override fun doInstanceDraw(context: RenderContext, model: ObjModel) = model.draw()
}