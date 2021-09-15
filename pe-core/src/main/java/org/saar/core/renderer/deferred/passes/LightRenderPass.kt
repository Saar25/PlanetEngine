package org.saar.core.renderer.deferred.passes

import org.saar.core.light.DirectionalLight
import org.saar.core.light.PointLight
import org.saar.core.light.ViewSpaceDirectionalLightUniform
import org.saar.core.light.ViewSpacePointLightUniform
import org.saar.core.renderer.deferred.DeferredRenderPass
import org.saar.core.renderer.deferred.DeferredRenderingBuffers
import org.saar.core.renderer.renderpass.RenderPassContext
import org.saar.core.renderer.renderpass.RenderPassPrototype
import org.saar.core.renderer.renderpass.RenderPassPrototypeWrapper
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.uniforms.IntUniform
import org.saar.lwjgl.opengl.shaders.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shaders.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.shaders.uniforms.UniformArray
import org.saar.maths.utils.Matrix4
import kotlin.math.max

class LightRenderPass(pointLights: Array<PointLight> = emptyArray(),
                      directionalLights: Array<DirectionalLight> = emptyArray()) : DeferredRenderPass {

    private val prototype = LightRenderPassPrototype(pointLights, directionalLights)
    private val wrapper = RenderPassPrototypeWrapper(this.prototype)

    constructor(light: DirectionalLight) : this(directionalLights = arrayOf(light))

    constructor(light: PointLight) : this(pointLights = arrayOf(light))

    override fun render(context: RenderPassContext, buffers: DeferredRenderingBuffers) = this.wrapper.render {
        this.prototype.colourTextureUniform.value = buffers.albedo
        this.prototype.normalTextureUniform.value = buffers.normal
        this.prototype.specularTextureUniform.value = buffers.specular
        this.prototype.depthTextureUniform.value = buffers.depth

        this.prototype.projectionMatrixInvUniform.value =
            context.camera.projection.matrix.invertPerspective(Matrix4.temp)

        this.prototype.directionalLightsUniform.forEach { it.camera = context.camera }
        this.prototype.pointLightsUniform.forEach { it.camera = context.camera }
    }

    override fun delete() {
        this.wrapper.delete()
    }
}

private class LightRenderPassPrototype(
    private val pointLights: Array<PointLight>,
    private val directionalLights: Array<DirectionalLight>) : RenderPassPrototype {

    @UniformProperty
    val colourTextureUniform = TextureUniformValue("u_colourTexture", 0)

    @UniformProperty
    val normalTextureUniform = TextureUniformValue("u_normalTexture", 1)

    @UniformProperty
    val specularTextureUniform = TextureUniformValue("u_specularTexture", 2)

    @UniformProperty
    val depthTextureUniform = TextureUniformValue("u_depthTexture", 3)

    @UniformProperty
    val projectionMatrixInvUniform = Mat4UniformValue("u_projectionMatrixInv")

    @UniformProperty
    val directionalLightsCountUniform = object : IntUniform() {
        override fun getName() = "u_directionalLightsCount"

        override fun getUniformValue() = this@LightRenderPassPrototype.directionalLights.size
    }

    @UniformProperty
    val directionalLightsUniform: UniformArray<ViewSpaceDirectionalLightUniform> =
        UniformArray("u_directionalLights", this.directionalLights.size) { name, index ->
            object : ViewSpaceDirectionalLightUniform(name) {
                override fun getUniformValue() = this@LightRenderPassPrototype.directionalLights[index]
            }
        }

    @UniformProperty
    val pointLightsCountUniform = object : IntUniform() {
        override fun getName() = "u_pointLightsCount"

        override fun getUniformValue() = this@LightRenderPassPrototype.pointLights.size
    }

    @UniformProperty
    val pointLightsUniform: UniformArray<ViewSpacePointLightUniform> =
        UniformArray("u_pointLights", this.pointLights.size) { name, index ->
            object : ViewSpacePointLightUniform(name) {
                override fun getUniformValue() = this@LightRenderPassPrototype.pointLights[index]
            }
        }

    override fun fragmentShader(): Shader = Shader.createFragment(GlslVersion.V400,
        ShaderCode.define("MAX_POINT_LIGHTS", max(this.pointLights.size, 1).toString()),
        ShaderCode.define("MAX_DIRECTIONAL_LIGHTS", max(this.directionalLights.size, 1).toString()),

        ShaderCode.loadSource("/shaders/deferred/light/light.fragment.glsl")
    )
}