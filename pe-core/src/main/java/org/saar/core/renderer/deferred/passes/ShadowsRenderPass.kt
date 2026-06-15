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
import org.saar.lwjgl.opengl.shader.ShadersProgram
import org.saar.lwjgl.opengl.shader.uniforms.IntUniformValue
import org.saar.lwjgl.opengl.shader.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.shader.uniforms.Vec2iUniformValue
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D
import org.saar.maths.utils.Matrix4

class ShadowsRenderPass(
    private val albedoBuffer: ReadOnlyTexture2D,
    private val normalSpecularBuffer: ReadOnlyTexture2D,
    private val depthBuffer: ReadOnlyTexture2D,
    private val shadowCamera: ICamera,
    private val shadowMap: ReadOnlyTexture2D,
    light: DirectionalLight
) : RenderPass {

    private val shadersLink = ShadowsShadersLink(light)
    private val uniformsLoader = ShadersUniformsLoader.from(this.shadersLink)

    override fun render(context: RenderContext) {
        this.shadersLink.shadersProgram.bind()
        this.shadersLink.shadowMatrixUniform.value =
            this.shadowCamera.projection.matrix.mul(
                this.shadowCamera.viewMatrix, Matrix4.temp
            )
        this.shadersLink.projectionMatrixInvUniform.value =
            context.camera.projection.matrix.invertPerspective(Matrix4.temp)
        this.shadersLink.viewMatrixInvUniform.value =
            context.camera.viewMatrix.invert(Matrix4.temp)
        this.shadersLink.pcfRadiusUniform.value = 2
        this.shadersLink.shadowMapUniform.value = this.shadowMap
        this.shadersLink.shadowMapSizeUniform.value = Vector2i(shadowMap.width, shadowMap.height)

        this.shadersLink.colourTextureUniform.value = this.albedoBuffer
        this.shadersLink.normalSpecularTexture.value = this.normalSpecularBuffer
        this.shadersLink.depthTextureUniform.value = this.depthBuffer

        this.shadersLink.lightUniform.camera = context.camera

        this.uniformsLoader.load()
        QuadMesh.draw()
    }

    override fun delete() = this.shadersLink.shadersProgram.delete()

    // TODO: make object
    private class ShadowsShadersLink(private val light: DirectionalLight) : ShadersLink {

        @UniformProperty
        val shadowMatrixUniform = Mat4UniformValue("u_shadowMatrix")

        @UniformProperty
        val projectionMatrixInvUniform = Mat4UniformValue("u_projectionMatrixInv")

        @UniformProperty
        val viewMatrixInvUniform = Mat4UniformValue("u_viewMatrixInv")

        @UniformProperty
        val pcfRadiusUniform = IntUniformValue("u_pcfRadius")

        @UniformProperty
        val lightUniform = ViewSpaceDirectionalLightUniform("u_light", this.light)

        @UniformProperty
        val shadowMapUniform = TextureUniformValue("u_shadowMap", 0)

        @UniformProperty
        val shadowMapSizeUniform = Vec2iUniformValue("u_shadowMapSize")

        @UniformProperty
        val colourTextureUniform = TextureUniformValue("u_colourTexture", 1)

        @UniformProperty
        val normalSpecularTexture = TextureUniformValue("u_normalSpecularTexture", 2)

        @UniformProperty
        val depthTextureUniform = TextureUniformValue("u_depthTexture", 3)

        override val shadersProgram: ShadersProgram = ShadersProgram.create(
            Shader.createVertex(GlslVersion.V400, Renderers.quadVertexShaderCode),
            Shader.createFragment(
                GlslVersion.V400,
                ShaderCode.define("MAX_DIRECTIONAL_LIGHTS", "1"),
                ShaderCode.define("SHADOW_BIAS", String.format("%.8f", 0.01f)),
                ShaderCode.loadSource("/shaders/deferred/shadow/shadow.fragment.glsl")
            ),
        )
    }
}