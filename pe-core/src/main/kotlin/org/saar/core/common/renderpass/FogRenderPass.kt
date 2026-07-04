package org.saar.core.common.renderpass

import org.saar.core.camera.ICamera
import org.saar.core.fog.FogDistance
import org.saar.core.fog.FogUniformValue
import org.saar.core.fog.IFog
import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.*
import org.saar.core.renderer.state.CompositeRenderState
import org.saar.core.renderer.state.DepthStencilRenderState
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.core.screen.OffScreen
import org.saar.core.screen.Screen
import org.saar.core.screen.buildScreen
import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.renderbuffer.RenderBuffer
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.ShadersProgram
import org.saar.lwjgl.opengl.shader.uniforms.IntUniformValue
import org.saar.lwjgl.opengl.shader.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.shader.uniforms.Vec3UniformValue
import org.saar.lwjgl.opengl.texture.MutableTexture2D
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D
import org.saar.maths.utils.Matrix4
import org.saar.rhi.depthstencil.DepthStencilState
import org.saar.rhi.depthstencil.StencilOpState

fun RenderGraph.Builder.fogPass(input: FogRenderPass.Input.() -> Unit): FogRenderPass.Output {
    val outputAlbedo = MutableTexture2D.create()
    val screen = buildScreen(width, height) {
        colorAttachment(outputAlbedo, InternalFormat.RGBA8)
        stencilAttachment(RenderBuffer.create(), InternalFormat.STENCIL_INDEX8)
    }

    fogPass(screen, input)

    return FogRenderPass.Output(screen, outputAlbedo)
}

fun RenderGraph.Builder.fogPass(screen: Screen, input: FogRenderPass.Input.() -> Unit) {
    val input = FogRenderPass.Input().apply(input)
    addPass(FogRenderPass(screen, input))
}

fun FogRenderPass(
    albedoBuffer: ReadOnlyTexture2D,
    depthBuffer: ReadOnlyTexture2D,
    camera: ICamera,
    fog: IFog,
    fogDistance: FogDistance
): FogRenderPass {
    val input = FogRenderPass.Input().apply {
        this.albedoBuffer = albedoBuffer
        this.depthBuffer = depthBuffer
        this.camera = camera
        this.fog = fog
        this.fogDistance = fogDistance
    }
    return FogRenderPass(null, input)
}

class FogRenderPass(private val screen: Screen?, private val input: Input) : RenderPass {

    class Input {
        lateinit var albedoBuffer: ReadOnlyTexture2D
        lateinit var depthBuffer: ReadOnlyTexture2D
        lateinit var camera: ICamera
        lateinit var fog: IFog
        lateinit var fogDistance: FogDistance
    }

    class Output(val screen: OffScreen, val albedo: MutableTexture2D)

    private val shadersLink = FogShadersLink
    private val uniformsLoader = ShadersUniformsLoader.from(this.shadersLink)

    override val renderState = CompositeRenderState(
        DepthStencilRenderState(
            DepthStencilState(stencil = StencilOpState.ALWAYS_WRITE)
        ),
    )

    override fun render(context: RenderContext) {
        this.screen?.setAsDraw()
        this.renderState.apply()

        this.shadersLink.shadersProgram.bind()
        this.shadersLink.textureUniform.value = this.input.albedoBuffer
        this.shadersLink.depthUniform.value = this.input.depthBuffer
        this.shadersLink.fogDistanceUniform.value = this.input.fogDistance.ordinal
        this.shadersLink.fogUniform.colorUniform.value.set(this.input.fog.color)
        this.shadersLink.fogUniform.startUniform.value = this.input.fog.start
        this.shadersLink.fogUniform.endUniform.value = this.input.fog.end

        this.shadersLink.projectionMatrixInvUniform.value =
            this.input.camera.projection.matrix.invertPerspective(Matrix4.temp)

        this.shadersLink.cameraPositionUniform.value.set(this.input.camera.transform.position.value)
        this.uniformsLoader.load()
        QuadMesh.draw()
    }

    override fun delete() = this.shadersLink.shadersProgram.delete()

    private object FogShadersLink : ShadersLink {

        @UniformProperty
        val textureUniform = TextureUniformValue("u_texture", 0)

        @UniformProperty
        val depthUniform = TextureUniformValue("u_depth", 1)

        @UniformProperty
        val fogUniform = FogUniformValue("u_fog")

        @UniformProperty
        val projectionMatrixInvUniform = Mat4UniformValue("u_projectionMatrixInv")

        @UniformProperty
        val cameraPositionUniform = Vec3UniformValue("u_cameraPosition")

        @UniformProperty
        val fogDistanceUniform = IntUniformValue("u_fogDistance")

        override val shadersProgram: ShadersProgram = ShadersProgram.create(
            Shader.createVertex(GlslVersion.V400, Renderers.quadVertexShaderCode),
            Shader.createFragment(
                GlslVersion.V400,
                ShaderCode.define("FD_DEPTH", FogDistance.DEPTH.ordinal.toString()),
                ShaderCode.define("FD_Y", FogDistance.Y.ordinal.toString()),
                ShaderCode.define("FD_XZ", FogDistance.XZ.ordinal.toString()),
                ShaderCode.define("FD_XYZ", FogDistance.XYZ.ordinal.toString()),
                ShaderCode.loadSource("/shaders/postprocessing/fog.pass.glsl")
            ),
        )
    }
}