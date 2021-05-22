package org.saar.core.renderer.renderpass.shadow

import org.joml.Matrix4f
import org.joml.Matrix4fc
import org.saar.core.camera.ICamera
import org.saar.core.light.DirectionalLight
import org.saar.core.light.ViewSpaceDirectionalLightUniform
import org.saar.core.renderer.deferred.DeferredRenderPass
import org.saar.core.renderer.deferred.DeferredRenderingBuffers
import org.saar.core.renderer.renderpass.RenderPassContext
import org.saar.core.renderer.renderpass.RenderPassPrototype
import org.saar.core.renderer.renderpass.RenderPassPrototypeWrapper
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.uniforms.*
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture
import org.saar.maths.utils.Matrix4

class ShadowsRenderPass(shadowCamera: ICamera, shadowMap: ReadOnlyTexture, light: DirectionalLight) :
    DeferredRenderPass,
    RenderPassPrototypeWrapper<ShadowsRenderingBuffers>(ShadowsRenderPassPrototype(shadowCamera, shadowMap, light)) {

    override fun render(context: RenderPassContext, buffers: DeferredRenderingBuffers) {
        super.render(context, ShadowsRenderingBuffers(buffers.albedo, buffers.normal, buffers.specular, buffers.depth))
    }
}

private val matrix: Matrix4f = Matrix4.create()

private class ShadowsRenderPassPrototype(private val shadowCamera: ICamera,
                                         private val shadowMap: ReadOnlyTexture,
                                         private val light: DirectionalLight)
    : RenderPassPrototype<ShadowsRenderingBuffers> {

    @UniformProperty
    private val shadowMatrixUniform = object : Mat4Uniform() {
        override fun getName(): String = "u_shadowMatrix"

        override fun getUniformValue(): Matrix4fc {
            return this@ShadowsRenderPassPrototype.shadowCamera.projection.matrix.mul(
                this@ShadowsRenderPassPrototype.shadowCamera.viewMatrix, matrix)
        }
    }

    @UniformProperty
    private val projectionMatrixInvUniform = Mat4UniformValue("u_projectionMatrixInv")

    @UniformProperty
    private val viewMatrixInvUniform = Mat4UniformValue("u_viewMatrixInv")

    @UniformProperty
    private val pcfRadiusUniform = object : IntUniform() {
        override fun getName(): String = "u_pcfRadius"

        override fun getUniformValue(): Int = 2
    }

    @UniformProperty
    private val lightUniform = object : ViewSpaceDirectionalLightUniform("u_light") {
        override fun getUniformValue() = this@ShadowsRenderPassPrototype.light
    }

    @UniformProperty
    private val shadowMapUniform = object : TextureUniform() {
        override fun getUnit(): Int = 0

        override fun getName(): String = "u_shadowMap"

        override fun getUniformValue(): ReadOnlyTexture {
            return this@ShadowsRenderPassPrototype.shadowMap
        }
    }

    @UniformProperty
    private val colourTextureUniform = TextureUniformValue("u_colourTexture", 1)

    @UniformProperty
    private val normalTextureUniform = TextureUniformValue("u_normalTexture", 2)

    @UniformProperty
    private val specularTextureUniform = TextureUniformValue("u_specularTexture", 3)

    @UniformProperty
    private val depthTextureUniform = TextureUniformValue("u_depthTexture", 4)

    override fun fragmentShader(): Shader = Shader.createFragment(GlslVersion.V400,
        ShaderCode.define("MAX_DIRECTIONAL_LIGHTS", "1"),

        ShaderCode.loadSource("/shaders/deferred/shadow/shadow.fragment.glsl")
    )

    override fun onRender(context: RenderPassContext, buffers: ShadowsRenderingBuffers) {
        this.colourTextureUniform.value = buffers.albedo
        this.normalTextureUniform.value = buffers.normal
        this.specularTextureUniform.value = buffers.specular
        this.depthTextureUniform.value = buffers.depth

        this.projectionMatrixInvUniform.value = context.camera.projection.matrix.invertPerspective(matrix)
        this.viewMatrixInvUniform.value = context.camera.viewMatrix.invert(matrix)
        this.lightUniform.camera = context.camera
    }
}