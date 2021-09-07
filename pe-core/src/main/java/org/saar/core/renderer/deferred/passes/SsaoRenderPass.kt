package org.saar.core.renderer.deferred.passes

import org.saar.core.renderer.deferred.DeferredRenderPass
import org.saar.core.renderer.deferred.DeferredRenderingBuffers
import org.saar.core.renderer.renderpass.RenderPassContext
import org.saar.core.renderer.renderpass.RenderPassPrototype
import org.saar.core.renderer.renderpass.RenderPassPrototypeWrapper
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shaders.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.shaders.uniforms.Vec3UniformValue
import org.saar.maths.utils.Matrix4

class SsaoRenderPass : DeferredRenderPass {

    private val prototype = SsaoRenderPassPrototype()
    private val wrapper = RenderPassPrototypeWrapper(this.prototype)

    override fun render(context: RenderPassContext, buffers: DeferredRenderingBuffers) = this.wrapper.render {
        this.prototype.colourTextureUniform.value = buffers.albedo
        this.prototype.normalTextureUniform.value = buffers.normal
        this.prototype.depthTextureUniform.value = buffers.depth

        this.prototype.cameraWorldPositionUniform.value = context.camera.transform.position.value
        this.prototype.projectionMatrixInvUniform.value =
            context.camera.projection.matrix.invertPerspective(Matrix4.temp)
        this.prototype.viewMatrixInvUniform.value = context.camera.viewMatrix.invert(Matrix4.temp)
    }

    override fun delete() {
        this.wrapper.delete()
    }
}

private class SsaoRenderPassPrototype : RenderPassPrototype {

    @UniformProperty
    val colourTextureUniform = TextureUniformValue("u_colourTexture", 0)

    @UniformProperty
    val normalTextureUniform = TextureUniformValue("u_normalTexture", 1)

    @UniformProperty
    val depthTextureUniform = TextureUniformValue("u_depthTexture", 2)

    @UniformProperty
    val cameraWorldPositionUniform = Vec3UniformValue("u_cameraWorldPosition")

    @UniformProperty
    val projectionMatrixInvUniform = Mat4UniformValue("u_projectionMatrixInv")

    @UniformProperty
    val viewMatrixInvUniform = Mat4UniformValue("u_viewMatrixInv")

    override fun fragmentShader(): Shader = Shader.createFragment(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/deferred/ssao/ssao.fragment.glsl"))
}