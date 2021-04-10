package org.saar.core.renderer.deferred.light

import org.joml.Matrix4f
import org.saar.core.light.DirectionalLight
import org.saar.core.light.DirectionalLightUniform
import org.saar.core.light.PointLight
import org.saar.core.renderer.deferred.RenderPass
import org.saar.core.renderer.deferred.RenderPassContext
import org.saar.core.renderer.deferred.RenderPassPrototype
import org.saar.core.renderer.deferred.RenderPassPrototypeWrapper
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.uniforms.*
import org.saar.maths.utils.Matrix4

private val light = DirectionalLight()
    .also { light -> light.colour.set(1.0f, 1.0f, 1.0f) }
    .also { light -> light.direction.set(-50f, -50f, -50f) }

private val matrix: Matrix4f = Matrix4.create()

class LightRenderPass : RenderPass, RenderPassPrototypeWrapper(
    LightRenderPassPrototype(emptyArray(), arrayOf(light)))

private class LightRenderPassPrototype(private val pointLights: Array<PointLight>,
                                       private val directionalLights: Array<DirectionalLight>) : RenderPassPrototype {

    @UniformProperty
    private val colourTextureUniform = TextureUniformValue("colourTexture", 0)

    @UniformProperty
    private val normalTextureUniform = TextureUniformValue("normalTexture", 1)

    @UniformProperty
    private val depthTextureUniform = TextureUniformValue("depthTexture", 2)

    @UniformProperty
    private val cameraWorldPositionUniform = Vec3UniformValue("cameraWorldPosition")

    @UniformProperty
    private val projectionMatrixInvUniform = Mat4UniformValue("projectionMatrixInv")

    @UniformProperty
    private val viewMatrixInvUniform = Mat4UniformValue("viewMatrixInv")

    @UniformProperty
    private val directionalLightsCountUniform = object : IntUniform() {
        override fun getName() = "directionalLightsCount"

        override fun getUniformValue() = directionalLights.size
    }

    @UniformProperty
    private val directionalLightsUniform =
        UniformArray("directionalLights", this.directionalLights.size) { name, index ->
            object : DirectionalLightUniform(name) {
                override fun getUniformValue() = directionalLights[index]
            }
        }

    override fun fragmentShader(): Shader = Shader.createFragment(GlslVersion.V400,
        ShaderCode.define("MAX_POINT_LIGHTS", this.pointLights.size.toString()),
        ShaderCode.define("MAX_DIRECTIONAL_LIGHTS", this.directionalLights.size.toString()),

        ShaderCode.loadSource("/shaders/common/light/light.struct.glsl"),
        ShaderCode.loadSource("/shaders/common/light/light.header.glsl"),
        ShaderCode.loadSource("/shaders/common/transform/transform.header.glsl"),

        ShaderCode.loadSource("/shaders/deferred/light/fragment.glsl"),

        ShaderCode.loadSource("/shaders/common/light/light.source.glsl"),
        ShaderCode.loadSource("/shaders/common/transform/transform.source.glsl"))

    override fun onRender(context: RenderPassContext) {
        this.colourTextureUniform.value = context.buffers.albedo
        this.normalTextureUniform.value = context.buffers.normal
        this.depthTextureUniform.value = context.buffers.depth

        this.cameraWorldPositionUniform.value = context.camera.transform.position.value
        this.projectionMatrixInvUniform.value = context.camera.projection.matrix.invertPerspective(matrix)
        this.viewMatrixInvUniform.value = context.camera.viewMatrix.invert(matrix)
    }
}