package org.saar.core.common.r3d

import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RendererPrototype
import org.saar.core.renderer.RendererPrototypeWrapper
import org.saar.core.renderer.shaders.ShaderProperty
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.core.renderer.uniforms.UniformTrigger
import org.saar.lwjgl.opengl.blend.BlendTest
import org.saar.lwjgl.opengl.depth.DepthTest
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.uniforms.FloatUniformValue
import org.saar.lwjgl.opengl.shaders.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.utils.GlUtils
import org.saar.maths.utils.Matrix4

object DeferredRenderer3D : RendererPrototypeWrapper<Model3D>(DeferredRendererPrototype3D())

private val matrix = Matrix4.create()

private class DeferredRendererPrototype3D : RendererPrototype<Model3D> {

    @UniformProperty(UniformTrigger.PER_INSTANCE)
    private val specularUniform = FloatUniformValue("u_specular")

    @UniformProperty(UniformTrigger.PER_INSTANCE)
    private val mvpMatrixUniform = Mat4UniformValue("u_mvpMatrix")

    @UniformProperty(UniformTrigger.PER_RENDER_CYCLE)
    private val normalMatrixUniform = Mat4UniformValue("u_normalMatrix")

    @ShaderProperty
    private val vertex = Shader.createVertex(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/r3d/r3d.vertex.glsl"))

    @ShaderProperty
    private val fragment = Shader.createFragment(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/r3d/r3d.dfragment.glsl"))

    override fun vertexAttributes() = arrayOf(
        "in_position", "in_colour", "in_transformation")

    override fun onRenderCycle(context: RenderContext) {
        GlUtils.setCullFace(context.hints.cullFace)
        GlUtils.setProvokingVertexFirst()
        BlendTest.applyAlpha()
        DepthTest.enable()

        this.normalMatrixUniform.value = context.camera.viewMatrix.invert(matrix).transpose()
    }

    override fun onInstanceDraw(context: RenderContext, model: Model3D) {
        this.specularUniform.value = model.specular

        val v = context.camera.viewMatrix
        val p = context.camera.projection.matrix
        val m = model.transform.transformationMatrix

        this.mvpMatrixUniform.value = p.mul(v, Matrix4.create()).mul(m)
    }

    override fun doInstanceDraw(context: RenderContext, model: Model3D) = model.draw()
}