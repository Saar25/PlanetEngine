package org.saar.core.common.renderpass

import org.saar.core.camera.ICamera
import org.saar.core.light.DirectionalLight
import org.saar.core.light.DirectionalLightUniform
import org.saar.core.light.PointLight
import org.saar.core.light.PointLightUniform
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
import org.saar.lwjgl.opengl.shader.uniforms.IntUniformValue
import org.saar.lwjgl.opengl.shader.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.shader.uniforms.UniformArray
import org.saar.lwjgl.opengl.stencil.StencilState
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D
import org.saar.maths.utils.Matrix4
import org.saar.maths.utils.Vector4
import kotlin.math.max

class LightRenderPass(
    private val albedoBuffer: ReadOnlyTexture2D,
    private val normalSpecularBuffer: ReadOnlyTexture2D,
    private val depthBuffer: ReadOnlyTexture2D,
    private val camera: ICamera,
    private val pointLights: Array<PointLight> = emptyArray(),
    private val directionalLights: Array<DirectionalLight> = emptyArray(),
) : RenderPass {

    private val shadersLink = LightShadersLink(this.pointLights.size, this.directionalLights.size)
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
            this.camera.projection.matrix.invertPerspective(Matrix4.temp.identity())

        val viewInvT = this.camera.viewMatrix.invert(Matrix4.create()).transpose()

        this.shadersLink.directionalLightsCountUniform.value = this.directionalLights.size
        this.directionalLights.forEachIndexed { index, light ->
            val vs = Vector4.of(light.direction, 0f).mul(viewInvT).also { it.w = 0f }.normalize()
            this.shadersLink.directionalLightsUniform.value[index].directionUniform.value.set(vs.x(), vs.y(), vs.z())
            this.shadersLink.directionalLightsUniform.value[index].colourUniform.value = light.colour
        }

        this.shadersLink.pointLightsCountUniform.value = this.pointLights.size
        this.pointLights.forEachIndexed { index, light ->
            val vs = Vector4.of(light.position, 1f).mul(this.camera.viewMatrix).let { it.div(it.w()) }
            this.shadersLink.pointLightsUniform.value[index].positionUniform.value.set(vs.x(), vs.y(), vs.z())
            this.shadersLink.pointLightsUniform.value[index].attenuationUniform.value.set(light.attenuation.vector3f)
            this.shadersLink.pointLightsUniform.value[index].colourUniform.value.set(light.colour)
        }

        this.uniformsLoader.load()
        QuadMesh.draw()
    }


    override fun delete() = this.shadersLink.shadersProgram.delete()

    private class LightShadersLink(pointLights: Int, directionalLights: Int) : ShadersLink {

        @UniformProperty
        val colourTextureUniform = TextureUniformValue("u_colourTexture", 0)

        @UniformProperty
        val normalSpecularTextureUniform = TextureUniformValue("u_normalSpecularTexture", 1)

        @UniformProperty
        val depthTextureUniform = TextureUniformValue("u_depthTexture", 2)

        @UniformProperty
        val projectionMatrixInvUniform = Mat4UniformValue("u_projectionMatrixInv")

        @UniformProperty
        val directionalLightsCountUniform = IntUniformValue("u_directionalLightsCount")

        @UniformProperty
        val directionalLightsUniform = UniformArray("u_directionalLights", directionalLights, ::DirectionalLightUniform)

        @UniformProperty
        val pointLightsCountUniform = IntUniformValue("u_pointLightsCount")

        @UniformProperty
        val pointLightsUniform = UniformArray("u_pointLights", pointLights, ::PointLightUniform)

        override val shadersProgram: ShadersProgram = ShadersProgram.create(
            Shader.createVertex(GlslVersion.V400, Renderers.quadVertexShaderCode),
            Shader.createFragment(
                GlslVersion.V400,
                ShaderCode.define("MAX_POINT_LIGHTS", max(pointLights, 1).toString()),
                ShaderCode.define("MAX_DIRECTIONAL_LIGHTS", max(directionalLights, 1).toString()),
                ShaderCode.loadSource("/shaders/deferred/light/light.fragment.glsl")
            ),
        )
    }
}
