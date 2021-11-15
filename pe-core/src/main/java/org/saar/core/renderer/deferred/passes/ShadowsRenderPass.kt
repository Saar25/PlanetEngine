package org.saar.core.renderer.deferred.passes

import org.joml.Vector2i
import org.saar.core.camera.ICamera
import org.saar.core.light.DirectionalLight
import org.saar.core.light.ViewSpaceDirectionalLightUniform
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.deferred.DeferredRenderPass
import org.saar.core.renderer.deferred.DeferredRenderingBuffers
import org.saar.core.renderer.renderpass.RenderPassPrototype
import org.saar.core.renderer.renderpass.RenderPassPrototypeWrapper
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.uniforms.*
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D
import org.saar.maths.utils.Matrix4

class ShadowsRenderPass(shadowCamera: ICamera, shadowMap: ReadOnlyTexture2D, light: DirectionalLight) :
    DeferredRenderPass {

    private val prototype = ShadowsRenderPassPrototype(shadowCamera, shadowMap, light)
    private val wrapper = RenderPassPrototypeWrapper(this.prototype)

    override fun render(context: RenderContext, buffers: DeferredRenderingBuffers) = this.wrapper.render {
        this.prototype.colourTextureUniform.value = buffers.albedo
        this.prototype.normalSpecularTexture.value = buffers.normalSpecular
        this.prototype.depthTextureUniform.value = buffers.depth

        this.prototype.projectionMatrixInvUniform.value =
            context.camera.projection.matrix.invertPerspective(Matrix4.temp)
        this.prototype.viewMatrixInvUniform.value = context.camera.viewMatrix.invert(Matrix4.temp)
        this.prototype.lightUniform.camera = context.camera
    }

    override fun delete() {
        this.wrapper.delete()
    }

}

private class ShadowsRenderPassPrototype(private val shadowCamera: ICamera,
                                         private val shadowMap: ReadOnlyTexture2D,
                                         private val light: DirectionalLight) : RenderPassPrototype {

    @UniformProperty
    private val shadowMatrixUniform = object : Mat4Uniform() {
        override val name = "u_shadowMatrix"

        override val value
            get() = this@ShadowsRenderPassPrototype.shadowCamera.projection.matrix.mul(
                this@ShadowsRenderPassPrototype.shadowCamera.viewMatrix, Matrix4.temp)

        override val transpose = false
    }

    @UniformProperty
    val projectionMatrixInvUniform = Mat4UniformValue("u_projectionMatrixInv")

    @UniformProperty
    val viewMatrixInvUniform = Mat4UniformValue("u_viewMatrixInv")

    @UniformProperty
    private val pcfRadiusUniform = object : IntUniform() {
        override val name = "u_pcfRadius"

        override val value get() = 2
    }

    @UniformProperty
    val lightUniform = ViewSpaceDirectionalLightUniform("u_light", this.light)

    @UniformProperty
    private val shadowMapUniform = object : TextureUniform() {
        override val name = "u_shadowMap"

        override val value get() = this@ShadowsRenderPassPrototype.shadowMap

        override val unit = 0
    }

    @UniformProperty
    private val shadowMapSizeUniform = object : Vec2iUniform() {
        override val name = "u_shadowMapSize"

        override val value = Vector2i()
            get() = field.set(shadowMap.width, shadowMap.height)
    }

    @UniformProperty
    val colourTextureUniform = TextureUniformValue("u_colourTexture", 1)

    @UniformProperty
    val normalSpecularTexture = TextureUniformValue("u_normalSpecularTexture", 2)

    @UniformProperty
    val depthTextureUniform = TextureUniformValue("u_depthTexture", 3)

    override val fragmentShader: Shader = Shader.createFragment(GlslVersion.V400,
        ShaderCode.define("MAX_DIRECTIONAL_LIGHTS", "1"),
        ShaderCode.define("SHADOW_BIAS", String.format("%.8f", 0.001f)),
        ShaderCode.loadSource("/shaders/deferred/shadow/shadow.fragment.glsl")
    )
}