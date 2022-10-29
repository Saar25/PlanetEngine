package org.saar.core.common.r3d

import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RendererPrototype
import org.saar.core.renderer.RendererPrototypeWrapper
import org.saar.core.renderer.shaders.ShaderProperty
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.core.renderer.uniforms.UniformTrigger
import org.saar.lwjgl.opengl.blend.BlendTest
import org.saar.lwjgl.opengl.depth.DepthTest
import org.saar.lwjgl.opengl.provokingvertex.ProvokingVertex
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.uniforms.FloatUniformValue
import org.saar.lwjgl.opengl.shader.uniforms.Mat4UniformValue
import org.saar.maths.utils.Matrix4

object DeferredRenderer3D : RendererPrototypeWrapper<Model3D>(DeferredRendererPrototype3D())

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
        ProvokingVertex.setFirst();
        BlendTest.disable()
        DepthTest.enable()

        this.normalMatrixUniform.value = context.camera.viewMatrix.invert(Matrix4.temp).transpose()
    }

    override fun onInstanceDraw(context: RenderContext, model: Model3D) {
        this.specularUniform.value = model.specular

        val v = context.camera.viewMatrix
        val p = context.camera.projection.matrix
        val m = model.transform.transformationMatrix

        this.mvpMatrixUniform.value = p.mul(v, Matrix4.temp).mul(m)
    }

    override fun doInstanceDraw(context: RenderContext, model: Model3D) = model.draw()
}