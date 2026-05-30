package org.saar.core.common.portal

import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RendererPrototype
import org.saar.core.renderer.RendererPrototypeWrapper
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.core.renderer.uniforms.UniformTrigger
import org.saar.lwjgl.opengl.blend.BlendTest
import org.saar.lwjgl.opengl.cullface.CullFace
import org.saar.lwjgl.opengl.depth.DepthTest
import org.saar.lwjgl.opengl.provokingvertex.ProvokingVertex
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.maths.utils.Matrix4

object PortalDeferredRenderer : RendererPrototypeWrapper<PortalModel>(PortalDeferredRendererPrototype())

private class PortalDeferredRendererPrototype : RendererPrototype<PortalModel> {

    @UniformProperty(UniformTrigger.PER_INSTANCE)
    private val mvpMatrixUniform = Mat4UniformValue("u_mvpMatrix")

    @UniformProperty(UniformTrigger.PER_RENDER_CYCLE)
    private val normalMatrixUniform = Mat4UniformValue("u_normalMatrix")

    @UniformProperty
    private val textureUniform = TextureUniformValue("u_texture", 0)

    override val shaders = arrayOf(
        Shader.createVertex(GlslVersion.V400, ShaderCode.loadSource("/shaders/portal/portal.vertex.glsl")),
        Shader.createFragment(GlslVersion.V400, ShaderCode.loadSource("/shaders/portal/portal.dfragment.glsl"))
    )

    override fun vertexAttributes() = arrayOf("in_position")

    override fun onRenderCycle(context: RenderContext) {
        ProvokingVertex.setFirst()
        BlendTest.disable()
        DepthTest.enable()
        CullFace.disable()

        this.normalMatrixUniform.value = context.camera.viewMatrix.invert(Matrix4.temp).transpose()
    }

    override fun onInstanceDraw(context: RenderContext, model: PortalModel) {
        val v = context.camera.viewMatrix
        val p = context.camera.projection.matrix
        val m = model.transform.transformationMatrix

        this.mvpMatrixUniform.value = p.mul(v, Matrix4.temp).mul(m)

        this.textureUniform.value = model.viewTexture
    }

    override fun doInstanceDraw(context: RenderContext, model: PortalModel) = model.draw()
}