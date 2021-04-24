package org.saar.core.renderer.deferred.shadow

import org.joml.Matrix4f
import org.joml.Matrix4fc
import org.saar.core.camera.ICamera
import org.saar.core.light.DirectionalLight
import org.saar.core.light.DirectionalLightUniform
import org.saar.core.renderer.*
import org.saar.core.renderer.deferred.*
import org.saar.core.renderer.uniforms.*
import org.saar.lwjgl.opengl.shaders.*
import org.saar.lwjgl.opengl.shaders.uniforms.*
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture
import org.saar.maths.utils.Matrix4

class ShadowsRenderPass(shadowCamera: ICamera,
                        shadowMap: ReadOnlyTexture,
                        light: DirectionalLight) : RenderPass,
    RenderPassPrototypeWrapper(ShadowsRenderPassPrototype(shadowCamera, shadowMap, light))

private val matrix: Matrix4f = Matrix4.create()

private class ShadowsRenderPassPrototype(private val shadowCamera: ICamera,
                                         private val shadowMap: ReadOnlyTexture,
                                         private val light: DirectionalLight) : RenderPassPrototype {

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
    private val cameraWorldPositionUniform = Vec3UniformValue("u_cameraWorldPosition")

    @UniformProperty
    private val pcfRadiusUniform = object : IntUniform() {
        override fun getName(): String = "u_pcfRadius"

        override fun getUniformValue(): Int = 2
    }

    @UniformProperty
    private val lightUniform = object : DirectionalLightUniform("u_light") {
        override fun getUniformValue(): DirectionalLight {
            return this@ShadowsRenderPassPrototype.light
        }
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
    private val depthTextureUniform = TextureUniformValue("u_depthTexture", 3)

    @UniformUpdaterProperty
    private val colourTextureUpdater = UniformUpdater<RenderPassContext> { context ->
        this@ShadowsRenderPassPrototype.colourTextureUniform.value = context.buffers.albedo
    }

    @UniformUpdaterProperty
    private val normalTextureUpdater = UniformUpdater<RenderPassContext> { context ->
        this@ShadowsRenderPassPrototype.normalTextureUniform.value = context.buffers.normal
    }

    @UniformUpdaterProperty
    private val depthTextureUpdater = UniformUpdater<RenderPassContext> { context ->
        this@ShadowsRenderPassPrototype.depthTextureUniform.value = context.buffers.depth
    }

    override fun fragmentShader(): Shader = Shader.createFragment(GlslVersion.V400,
        ShaderCode.define("MAX_DIRECTIONAL_LIGHTS", "1"),

        ShaderCode.loadSource("/shaders/deferred/shadow/shadow.fragment.glsl")
    )

    override fun onRender(context: RenderPassContext) {
        this.projectionMatrixInvUniform.value = context.camera.projection.matrix.invertPerspective(matrix)
        this.cameraWorldPositionUniform.value = context.camera.transform.position.value
        this.viewMatrixInvUniform.value = context.camera.viewMatrix.invert(matrix)
    }
}