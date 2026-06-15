package org.saar.core.renderer.deferred.passes

import org.saar.core.light.DirectionalLight
import org.saar.core.light.PointLight
import org.saar.core.light.ViewSpaceDirectionalLightUniform
import org.saar.core.light.ViewSpacePointLightUniform
import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.*
import org.saar.core.renderer.state.*
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.blend.BlendState
import org.saar.lwjgl.opengl.cullface.CullFaceState
import org.saar.lwjgl.opengl.depth.DepthState
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.ShadersProgram
import org.saar.lwjgl.opengl.shader.uniforms.IntUniform
import org.saar.lwjgl.opengl.shader.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.shader.uniforms.UniformArray
import org.saar.lwjgl.opengl.stencil.StencilState
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D
import org.saar.maths.utils.Matrix4
import kotlin.math.max

class LightRenderPass(
    private val albedoBuffer: ReadOnlyTexture2D,
    private val normalSpecularBuffer: ReadOnlyTexture2D,
    private val depthBuffer: ReadOnlyTexture2D,
    pointLights: Array<PointLight> = emptyArray(),
    directionalLights: Array<DirectionalLight> = emptyArray()
) : RenderPass {

    private val shadersLink = LightShadersLink(pointLights, directionalLights)
    private val uniformsLoader = ShadersUniformsLoader.from(this.shadersLink)

    override val renderState = CompositeRenderState(
        StencilTestRenderState(StencilState.REPLACE),
        DepthTestRenderState(DepthState.DISABLED),
        BlendTestRenderState(BlendState.DISABLED),
        CullFaceRenderState(CullFaceState.DISABLED),
    )

    override fun render(context: RenderContext) {
        this.shadersLink.shadersProgram.bind()
        this.shadersLink.colourTextureUniform.value = this.albedoBuffer
        this.shadersLink.normalSpecularTextureUniform.value = this.normalSpecularBuffer
        this.shadersLink.depthTextureUniform.value = this.depthBuffer

        this.shadersLink.projectionMatrixInvUniform.value =
            context.camera.projection.matrix.invertPerspective(Matrix4.temp.identity())

        this.shadersLink.directionalLightsUniform.forEach { it.camera = context.camera }
        this.shadersLink.pointLightsUniform.forEach { it.camera = context.camera }

        this.uniformsLoader.load()
        QuadMesh.draw()
    }


    override fun delete() = this.shadersLink.shadersProgram.delete()
}

// TODO: make object
private class LightShadersLink(
    private val pointLights: Array<PointLight>,
    private val directionalLights: Array<DirectionalLight>
) : ShadersLink {

    @UniformProperty
    val colourTextureUniform = TextureUniformValue("u_colourTexture", 0)

    @UniformProperty
    val normalSpecularTextureUniform = TextureUniformValue("u_normalSpecularTexture", 1)

    @UniformProperty
    val depthTextureUniform = TextureUniformValue("u_depthTexture", 2)

    @UniformProperty
    val projectionMatrixInvUniform = Mat4UniformValue("u_projectionMatrixInv")

    @UniformProperty
    val directionalLightsCountUniform = object : IntUniform() {
        override val name = "u_directionalLightsCount"

        override val value get() = this@LightShadersLink.directionalLights.size
    }

    @UniformProperty
    val directionalLightsUniform: UniformArray<ViewSpaceDirectionalLightUniform> =
        UniformArray("u_directionalLights", this.directionalLights.size) { name, index ->
            ViewSpaceDirectionalLightUniform(name, this@LightShadersLink.directionalLights[index])
        }

    @UniformProperty
    val pointLightsCountUniform = object : IntUniform() {
        override val name = "u_pointLightsCount"

        override val value get() = this@LightShadersLink.pointLights.size
    }

    @UniformProperty
    val pointLightsUniform: UniformArray<ViewSpacePointLightUniform> =
        UniformArray("u_pointLights", this.pointLights.size) { name, index ->
            ViewSpacePointLightUniform(name, this@LightShadersLink.pointLights[index])
        }

    override val shadersProgram: ShadersProgram = ShadersProgram.create(
        Shader.createVertex(GlslVersion.V400, Renderers.quadVertexShaderCode),
        Shader.createFragment(
            GlslVersion.V400,
            ShaderCode.define("MAX_POINT_LIGHTS", max(this.pointLights.size, 1).toString()),
            ShaderCode.define("MAX_DIRECTIONAL_LIGHTS", max(this.directionalLights.size, 1).toString()),
            ShaderCode.loadSource("/shaders/deferred/light/light.fragment.glsl")
        ),
    )
}