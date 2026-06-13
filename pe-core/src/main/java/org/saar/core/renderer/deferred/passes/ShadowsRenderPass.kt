package org.saar.core.renderer.deferred.passes

import org.joml.Vector2i
import org.saar.core.camera.ICamera
import org.saar.core.light.DirectionalLight
import org.saar.core.light.ViewSpaceDirectionalLightUniform
import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.*
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.uniforms.*
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D
import org.saar.maths.utils.Matrix4

class ShadowsRenderPass(
    private val albedoBuffer: ReadOnlyTexture2D,
    private val normalSpecularBuffer: ReadOnlyTexture2D,
    private val depthBuffer: ReadOnlyTexture2D,
    shadowCamera: ICamera,
    shadowMap: ReadOnlyTexture2D,
    light: DirectionalLight
) : RenderNode {

    private val prototype = ShadowsRenderPassPrototype(shadowCamera, shadowMap, light)
    private val wrapper = RendererPrototypeHelper(this.prototype)

    override fun render(context: RenderContext) = this.wrapper.render(context) {
        this.prototype.colourTextureUniform.value = this.albedoBuffer
        this.prototype.normalSpecularTexture.value = this.normalSpecularBuffer
        this.prototype.depthTextureUniform.value = this.depthBuffer

        this.prototype.projectionMatrixInvUniform.value =
            context.camera.projection.matrix.invertPerspective(Matrix4.temp)
        this.prototype.viewMatrixInvUniform.value = context.camera.viewMatrix.invert(Matrix4.temp)
        this.prototype.lightUniform.camera = context.camera
    }

    override fun delete() {
        this.wrapper.delete()
    }

}

private class ShadowsRenderPassPrototype(
    private val shadowCamera: ICamera,
    private val shadowMap: ReadOnlyTexture2D,
    private val light: DirectionalLight
) : RendererPrototype<Unit> {

    @UniformProperty
    private val shadowMatrixUniform = object : Mat4Uniform() {
        override val name = "u_shadowMatrix"

        override val value
            get() = this@ShadowsRenderPassPrototype.shadowCamera.projection.matrix.mul(
                this@ShadowsRenderPassPrototype.shadowCamera.viewMatrix, Matrix4.temp
            )

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

    override val shaders = arrayOf(
        Shader.createVertex(GlslVersion.V400, Renderers.vertexShaderCode),
        Shader.createFragment(
            GlslVersion.V400,
            ShaderCode.define("MAX_DIRECTIONAL_LIGHTS", "1"),
            ShaderCode.define("SHADOW_BIAS", String.format("%.8f", 0.01f)),
            ShaderCode.loadSource("/shaders/deferred/shadow/shadow.fragment.glsl")
        ),
    )

    override fun doInstanceDraw(context: RenderContext, model: Unit) = QuadMesh.draw()
}