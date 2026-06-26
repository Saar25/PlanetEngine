package org.saar.core.common.renderpass

import org.saar.core.camera.ICamera
import org.saar.core.fog.FogDistance
import org.saar.core.fog.FogUniformValue
import org.saar.core.fog.IFog
import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.*
import org.saar.core.renderer.state.CompositeRenderState
import org.saar.core.renderer.state.DepthTestRenderState
import org.saar.core.renderer.state.StencilTestRenderState
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.core.screen.MainScreen
import org.saar.core.screen.Screen
import org.saar.core.screen.buildScreen
import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.depth.DepthState
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.ShadersProgram
import org.saar.lwjgl.opengl.shader.uniforms.IntUniformValue
import org.saar.lwjgl.opengl.shader.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.shader.uniforms.Vec3UniformValue
import org.saar.lwjgl.opengl.stencil.StencilState
import org.saar.lwjgl.opengl.texture.MutableTexture2D
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D
import org.saar.maths.utils.Matrix4

fun RenderGraph.Builder.fogPass(block: FogRenderPass.Builder.() -> Unit): FogRenderPass.Output {
    val builder = FogRenderPass.Builder().apply(block)
    val renderPass = FogRenderPass(
        builder.input.albedoBuffer,
        builder.input.depthBuffer,
        builder.input.camera,
        builder.input.fog,
        builder.input.fogDistance,
    )
    val outputAlbedo = builder.output?.albedo ?: MutableTexture2D.create()
    val screen = builder.output?.screen ?: buildScreen(width, height) {
        colorAttachment(outputAlbedo, InternalFormat.RGBA8)
    }
    addPass(renderPass.onto(screen))
    return FogRenderPass.Output(screen, outputAlbedo)
}

class FogRenderPass(
    private val albedoBuffer: ReadOnlyTexture2D,
    private val depthBuffer: ReadOnlyTexture2D,
    private val camera: ICamera,
    private val fog: IFog,
    private val fogDistance: FogDistance
) : RenderPass {

    class Input(
        val albedoBuffer: ReadOnlyTexture2D,
        val depthBuffer: ReadOnlyTexture2D,
        val camera: ICamera,
        val fog: IFog,
        val fogDistance: FogDistance,
    )

    class Output(val screen: Screen, val albedo: MutableTexture2D)

    class Builder {
        lateinit var input: Input
        var output: Output? = null

        fun outputMainScreen() {
            this.output = Output(MainScreen, MutableTexture2D.NULL)
        }
    }

    private val shadersLink = FogShadersLink
    private val uniformsLoader = ShadersUniformsLoader.from(this.shadersLink)

    override val renderState = CompositeRenderState(
        StencilTestRenderState(StencilState.REPLACE),
        DepthTestRenderState(DepthState.DISABLED),
    )

    override fun render(context: RenderContext) {
        this.shadersLink.shadersProgram.bind()
        this.shadersLink.textureUniform.value = this.albedoBuffer
        this.shadersLink.depthUniform.value = this.depthBuffer
        this.shadersLink.fogDistanceUniform.value = this.fogDistance.ordinal
        this.shadersLink.fogUniform.colorUniform.value.set(this.fog.color)
        this.shadersLink.fogUniform.startUniform.value = this.fog.start
        this.shadersLink.fogUniform.endUniform.value = this.fog.end

        this.shadersLink.projectionMatrixInvUniform.value =
            this.camera.projection.matrix.invertPerspective(Matrix4.temp)

        this.shadersLink.cameraPositionUniform.value.set(this.camera.transform.position.value)
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