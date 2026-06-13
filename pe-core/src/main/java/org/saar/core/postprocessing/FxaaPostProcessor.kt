package org.saar.core.postprocessing

import org.joml.Vector2i
import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.*
import org.saar.core.renderer.state.CompositeRenderState
import org.saar.core.renderer.state.StencilTestRenderState
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.core.screen.MainScreen
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.ShadersProgram
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.shader.uniforms.Vec2iUniform
import org.saar.lwjgl.opengl.stencil.StencilState
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D

class FxaaPostProcessor(
    private val albedoBuffer: ReadOnlyTexture2D,
) : RenderPass {

    private val prototype = FxaaPostProcessorPrototype()
    private val wrapper = RendererPrototypeHelper(this.prototype)

    override val renderState = CompositeRenderState(
        StencilTestRenderState(StencilState.DISABLED),
    )

    override fun render(context: RenderContext) = this.wrapper.render(context) {
        this.prototype.textureUniform.value = this.albedoBuffer
    }

    override fun delete() = this.wrapper.delete()
}

private class FxaaPostProcessorPrototype : RendererPrototype<Unit> {

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

    override fun doInstanceDraw(context: RenderContext, model: Unit) = QuadMesh.draw()
}