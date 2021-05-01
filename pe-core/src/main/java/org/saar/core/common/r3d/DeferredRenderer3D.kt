package org.saar.core.common.r3d

import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RendererPrototype
import org.saar.core.renderer.RendererPrototypeWrapper
import org.saar.core.renderer.deferred.DeferredRenderer
import org.saar.core.renderer.shaders.ShaderProperty
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.core.renderer.uniforms.UniformTrigger
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.ShaderType
import org.saar.lwjgl.opengl.shaders.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.utils.GlUtils
import org.saar.maths.utils.Matrix4

class DeferredRenderer3D(private vararg val models: Model3D) : DeferredRenderer,
    RendererPrototypeWrapper<Model3D>(DeferredRendererPrototype3D()) {

    override fun render(context: RenderContext, vararg models: Model3D) {
        super.render(context, *this.models, *models)
    }

    override fun render(context: RenderContext) {
        super.render(context, *this.models)
    }

    override fun delete() {
        this.models.forEach { it.delete() }
    }
}

private class DeferredRendererPrototype3D : RendererPrototype<Model3D> {

    @UniformProperty(UniformTrigger.PER_INSTANCE)
    private val mvpMatrixUniform = Mat4UniformValue("u_mvpMatrix")

    @ShaderProperty(ShaderType.VERTEX)
    private val vertex = Shader.createVertex(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/r3d/r3d.vertex.glsl"))

    @ShaderProperty(ShaderType.FRAGMENT)
    private val fragment = Shader.createFragment(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/r3d/r3d.dfragment.glsl"))

    override fun vertexAttributes() = arrayOf(
        "in_position", "in_colour", "in_transformation")

    override fun onRenderCycle(context: RenderContext) {
        GlUtils.setCullFace(context.hints.cullFace)
        GlUtils.enableAlphaBlending()
        GlUtils.enableDepthTest()
        GlUtils.setProvokingVertexFirst()
    }

    override fun onInstanceDraw(context: RenderContext, model: Model3D) {
        val v = context.camera.viewMatrix
        val p = context.camera.projection.matrix
        val m = model.transform.transformationMatrix

        this.mvpMatrixUniform.value = p.mul(v, Matrix4.create()).mul(m)
    }
}