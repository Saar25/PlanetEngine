package org.saar.core.renderer.deferred.light

import org.joml.Matrix4f
import org.saar.core.light.DirectionalLight
import org.saar.core.light.DirectionalLightUniform
import org.saar.core.light.PointLight
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
import org.saar.maths.utils.Matrix4
import kotlin.math.max

private val light = DirectionalLight()
    .also { light -> light.colour.set(1.0f, 1.0f, 1.0f) }
    .also { light -> light.direction.set(-50f, -50f, -50f) }

private val matrix: Matrix4f = Matrix4.create()

class LightRenderPass : DeferredRenderPass,
    RenderPassPrototypeWrapper<LightRenderingBuffers>(LightRenderPassPrototype(emptyArray(), arrayOf(light))) {

    override fun render(context: RenderPassContext, buffers: DeferredRenderingBuffers) {
        super.render(context, LightRenderingBuffers(buffers.albedo, buffers.normal, buffers.depth))
    }
}

private class LightRenderPassPrototype(private val pointLights: Array<PointLight>,
                                       private val directionalLights: Array<DirectionalLight>)
    : RenderPassPrototype<LightRenderingBuffers> {

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

    @UniformProperty
    private val directionalLightsCountUniform = object : IntUniform() {
        override fun getName() = "u_directionalLightsCount"

        override fun getUniformValue() = directionalLights.size
    }

    @UniformProperty
    private val directionalLightsUniform =
        UniformArray("u_directionalLights", this.directionalLights.size) { name, index ->
            object : DirectionalLightUniform(name) {
                override fun getUniformValue() = directionalLights[index]
            }
        }

    override fun fragmentShader(): Shader = Shader.createFragment(GlslVersion.V400,
        ShaderCode.define("MAX_POINT_LIGHTS", max(this.pointLights.size, 1).toString()),
        ShaderCode.define("MAX_DIRECTIONAL_LIGHTS", max(this.directionalLights.size, 1).toString()),

        ShaderCode.loadSource("/shaders/deferred/light/light.fragment.glsl")
    )

    override fun onRender(context: RenderPassContext, buffers: LightRenderingBuffers) {
        this.colourTextureUniform.value = buffers.albedo
        this.normalTextureUniform.value = buffers.normal
        this.depthTextureUniform.value = buffers.depth

        this.cameraWorldPositionUniform.value = context.camera.transform.position.value
        this.projectionMatrixInvUniform.value = context.camera.projection.matrix.invertPerspective(matrix)
        this.viewMatrixInvUniform.value = context.camera.viewMatrix.invert(matrix)
    }
}