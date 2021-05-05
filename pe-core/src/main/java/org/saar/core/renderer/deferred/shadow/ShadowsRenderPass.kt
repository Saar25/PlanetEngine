package org.saar.core.renderer.deferred.shadow

import org.joml.Matrix4f
import org.joml.Matrix4fc
import org.saar.core.camera.ICamera
import org.saar.core.light.DirectionalLight
import org.saar.core.light.DirectionalLightUniform
import org.saar.core.renderer.deferred.RenderPass
import org.saar.core.renderer.deferred.RenderPassContext
import org.saar.core.renderer.deferred.RenderPassPrototype
import org.saar.core.renderer.deferred.RenderPassPrototypeWrapper
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
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

    override fun fragmentShader(): Shader = Shader.createFragment(GlslVersion.V400,
        ShaderCode.define("MAX_DIRECTIONAL_LIGHTS", "1"),

        ShaderCode.loadSource("/shaders/deferred/shadow/shadow.fragment.glsl")
    )

    override fun onRender(context: RenderPassContext) {
        this.projectionMatrixInvUniform.value = context.camera.projection.matrix.invertPerspective(matrix)
        this.cameraWorldPositionUniform.value = context.camera.transform.position.value
        this.viewMatrixInvUniform.value = context.camera.viewMatrix.invert(matrix)

        this.colourTextureUniform.value = context.buffers.albedo
        this.normalTextureUniform.value = context.buffers.normal
        this.depthTextureUniform.value = context.buffers.depth
    }
}