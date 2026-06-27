package org.saar.core.common.renderpass

import org.joml.Vector2i
import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.*
import org.saar.core.renderer.state.StencilTestRenderState
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.core.screen.MainScreen
import org.saar.core.screen.OffScreen
import org.saar.core.screen.Screen
import org.saar.core.screen.buildScreen
import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.ShadersProgram
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.shader.uniforms.Vec2iUniform
import org.saar.lwjgl.opengl.stencil.StencilState
import org.saar.lwjgl.opengl.texture.MutableTexture2D
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D

fun RenderGraph.Builder.fxaaPass(input: FxaaPostProcessor.Input.() -> Unit): FxaaPostProcessor.Output {
    val outputAlbedo = MutableTexture2D.create()
    val screen = buildScreen(width, height) {
        colorAttachment(outputAlbedo, InternalFormat.RGBA8)
    }

    fxaaPass(screen, input)

    return FxaaPostProcessor.Output(screen, outputAlbedo)
}

fun RenderGraph.Builder.fxaaPass(screen: Screen, input: FxaaPostProcessor.Input.() -> Unit) {
    val input = FxaaPostProcessor.Input().apply(input)
    addPass(FxaaPostProcessor(screen, input))
}

fun FxaaPostProcessor(albedoBuffer: ReadOnlyTexture2D): FxaaPostProcessor {
    val input = FxaaPostProcessor.Input().apply {
        this.albedoBuffer = albedoBuffer
    }
    return FxaaPostProcessor(null, input)
}

class FxaaPostProcessor(private val screen: Screen?, private val input: Input) : RenderPass {

    class Input {
        lateinit var albedoBuffer: ReadOnlyTexture2D
    }

    class Output(val screen: OffScreen, val albedo: MutableTexture2D)

    private val shadersLink = FxaaShadersLink
    private val uniformsLoader = ShadersUniformsLoader.from(this.shadersLink)

    override val renderState = StencilTestRenderState(StencilState.DISABLED)

    override fun render(context: RenderContext) {
        this.screen?.setAsDraw()
        this.renderState.apply()

        this.shadersLink.shadersProgram.bind()
        this.shadersLink.textureUniform.value = this.input.albedoBuffer

        this.uniformsLoader.load()

        QuadMesh.draw()
    }

    override fun delete() = this.shadersLink.shadersProgram.delete()

    private object FxaaShadersLink : ShadersLink {

        @UniformProperty
        val textureUniform = TextureUniformValue("u_texture", 0)

        @UniformProperty
        val resolutionUniform = object : Vec2iUniform() {
            override val name = "u_resolution"

            override val value = Vector2i()
                get() = field.set(MainScreen.width, MainScreen.height)
        }

        override val shadersProgram: ShadersProgram = ShadersProgram.create(
            Shader.createVertex(GlslVersion.V400, Renderers.quadVertexShaderCode),
            Shader.createFragment(
                GlslVersion.V400,
                ShaderCode.define("FXAA_REDUCE_MIN", (1.0 / 128.0).toString()),
                ShaderCode.define("FXAA_REDUCE_MUL", (1.0 / 8.0).toString()),
                ShaderCode.define("FXAA_SPAN_MAX", 8.0.toString()),
                ShaderCode.loadSource("/shaders/postprocessing/fxaa.pass.glsl")
            ),
        )
    }
}