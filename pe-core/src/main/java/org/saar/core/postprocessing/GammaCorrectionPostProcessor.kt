package org.saar.core.postprocessing

import org.joml.Vector2i
import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.*
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.core.screen.MainScreen
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.uniforms.FloatUniformValue
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.shader.uniforms.Vec2iUniform
import org.saar.lwjgl.opengl.stencil.StencilTest
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D

class GammaCorrectionPostProcessor(
    private val albedoBuffer: ReadOnlyTexture2D,
    private val gamma: Float = 2.2f,
) : RenderPass {

    private val prototype = GammaCorrectionPostProcessorPrototype()
    private val wrapper = RendererPrototypeHelper(this.prototype)

    override fun render(context: RenderContext) = this.wrapper.render(context) {
        StencilTest.disable()

        this.prototype.textureUniform.value = this.albedoBuffer
        this.prototype.gammaUniform.value = this.gamma
    }

    override fun delete() {
        this.wrapper.delete()
    }
}

private class GammaCorrectionPostProcessorPrototype : RendererPrototype<Unit> {

    @UniformProperty
    val textureUniform = TextureUniformValue("u_texture", 0)

    @UniformProperty
    val resolutionUniform = object : Vec2iUniform() {
        override val name = "u_resolution"

        override val value = Vector2i()
            get() = field.set(MainScreen.width, MainScreen.height)
    }

    @UniformProperty
    val gammaUniform = FloatUniformValue("u_gamma")

    override val shaders = arrayOf(
        Shader.createVertex(GlslVersion.V400, Renderers.vertexShaderCode),
        Shader.createFragment(
            GlslVersion.V400,
            ShaderCode.loadSource("/shaders/postprocessing/gamma-correction.pass.glsl")
        ),
    )

    override fun doInstanceDraw(context: RenderContext, model: Unit) = QuadMesh.draw()
}