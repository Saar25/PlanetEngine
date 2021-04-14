package org.saar.core.renderer.deferred.ssao

import org.joml.Matrix4f
import org.saar.core.renderer.deferred.RenderPass
import org.saar.core.renderer.deferred.RenderPassContext
import org.saar.core.renderer.deferred.RenderPassPrototype
import org.saar.core.renderer.deferred.RenderPassPrototypeWrapper
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shaders.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.shaders.uniforms.Vec3UniformValue
import org.saar.maths.utils.Matrix4

private val matrix: Matrix4f = Matrix4.create()

class SsaoRenderPass : RenderPass, RenderPassPrototypeWrapper(SsaoRenderPassPrototype())

private class SsaoRenderPassPrototype : RenderPassPrototype {

    @UniformProperty
    private val colourTextureUniform = TextureUniformValue("u_colourTexture", 0)

    @UniformProperty
    private val normalTextureUniform = TextureUniformValue("u_normalTexture", 1)

    @UniformProperty
    private val depthTextureUniform = TextureUniformValue("u_depthTexture", 2)

    @UniformProperty
    private val cameraWorldPositionUniform = Vec3UniformValue("u_cameraWorldPosition")

    @UniformProperty
    private val projectionMatrixInvUniform = Mat4UniformValue("u_projectionMatrixInv")

    @UniformProperty
    private val viewMatrixInvUniform = Mat4UniformValue("u_viewMatrixInv")

    override fun fragmentShader(): Shader = Shader.createFragment(GlslVersion.V400,

        ShaderCode.loadSource("/shaders/deferred/ssao/ssao.fragment.glsl")
    )

    override fun onRender(context: RenderPassContext) {
        this.colourTextureUniform.value = context.buffers.albedo
        this.normalTextureUniform.value = context.buffers.normal
        this.depthTextureUniform.value = context.buffers.depth

        this.cameraWorldPositionUniform.value = context.camera.transform.position.value
        this.projectionMatrixInvUniform.value = context.camera.projection.matrix.invertPerspective(matrix)
        this.viewMatrixInvUniform.value = context.camera.viewMatrix.invert(matrix)
    }
}