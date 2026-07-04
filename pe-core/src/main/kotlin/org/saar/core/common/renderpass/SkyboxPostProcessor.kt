package org.saar.core.common.renderpass

import org.saar.core.camera.ICamera
import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.*
import org.saar.core.renderer.state.BlendRenderState
import org.saar.core.renderer.state.CompositeRenderState
import org.saar.core.renderer.state.DepthStencilRenderState
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.core.screen.OffScreen
import org.saar.core.screen.Screen
import org.saar.core.screen.buildScreen
import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.ShadersProgram
import org.saar.lwjgl.opengl.shader.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.texture.CubeMapTexture
import org.saar.lwjgl.opengl.texture.MutableTexture2D
import org.saar.maths.utils.Matrix4
import org.saar.rhi.blending.BlendAttachmentState
import org.saar.rhi.blending.BlendFactor
import org.saar.rhi.blending.BlendState
import org.saar.rhi.depthstencil.DepthStencilState
import org.saar.rhi.depthstencil.StencilOpState

fun RenderGraph.Builder.skyboxPass(input: SkyboxPostProcessor.Input.() -> Unit): SkyboxPostProcessor.Output {
    val outputAlbedo = MutableTexture2D.create()
    val screen = buildScreen(width, height) {
        colorAttachment(outputAlbedo, InternalFormat.RGBA8)
    }

    skyboxPass(screen, input)

    return SkyboxPostProcessor.Output(screen, outputAlbedo)
}

fun RenderGraph.Builder.skyboxPass(screen: Screen, input: SkyboxPostProcessor.Input.() -> Unit) {
    val input = SkyboxPostProcessor.Input().apply(input)
    addPass(SkyboxPostProcessor(screen, input))
}

fun SkyboxPostProcessor(cubeMap: CubeMapTexture, camera: ICamera): SkyboxPostProcessor {
    val input = SkyboxPostProcessor.Input().apply {
        this.cubeMap = cubeMap
        this.camera = camera
    }
    return SkyboxPostProcessor(null, input)
}

class SkyboxPostProcessor(val screen: Screen?, val input: Input) : RenderPass {

    class Input {
        lateinit var cubeMap: CubeMapTexture
        lateinit var camera: ICamera
    }

    class Output(val screen: OffScreen, val albedo: MutableTexture2D)

    private val shadersLink = SkyboxShadersLink
    private val uniformsLoader = ShadersUniformsLoader.from(this.shadersLink)

    override val renderState = CompositeRenderState(
        DepthStencilRenderState(
            DepthStencilState(stencil = StencilOpState.ALWAYS_WRITE)
        ),
        BlendRenderState(
            BlendState(
                attachment = BlendAttachmentState(
                    blendEnable = true,
                    srcColorFactor = BlendFactor.ONE_MINUS_DST_ALPHA,
                    dstColorFactor = BlendFactor.DST_ALPHA,
                )
            )
        )
    )

    override fun render(context: RenderContext) {
        this.screen?.setAsDraw()
        this.renderState.apply()

        this.shadersLink.shadersProgram.bind()
        this.shadersLink.projectionMatrixInvUniform.value = this.input.camera.projection.matrix.invert(Matrix4.temp)
        this.shadersLink.viewMatrixInvUniform.value = this.input.camera.viewMatrix.invert(Matrix4.temp)
        this.shadersLink.cubeMapUniform.value = this.input.cubeMap

        this.uniformsLoader.load()
        QuadMesh.draw()
    }

    override fun delete() {
        this.shadersLink.shadersProgram.delete()
        this.input.cubeMap.delete()
    }

    private object SkyboxShadersLink : ShadersLink {

        @UniformProperty
        val cubeMapUniform = TextureUniformValue("u_cubeMap", 0)

        @UniformProperty
        val projectionMatrixInvUniform = Mat4UniformValue("u_projectionMatrixInv")

        @UniformProperty
        val viewMatrixInvUniform = Mat4UniformValue("u_viewMatrixInv")

        override val shadersProgram: ShadersProgram = ShadersProgram.create(
            Shader.createVertex(GlslVersion.V400, ShaderCode.loadSource("/shaders/postprocessing/skybox.vertex.glsl")),
            Shader.createFragment(GlslVersion.V400, ShaderCode.loadSource("/shaders/postprocessing/skybox.pass.glsl")),
        )
    }
}