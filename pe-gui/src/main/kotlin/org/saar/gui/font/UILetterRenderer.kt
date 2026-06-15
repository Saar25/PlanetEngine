package org.saar.gui.font

import org.joml.Vector2i
import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.Renderer
import org.saar.core.renderer.RendererPrototype
import org.saar.core.renderer.RendererPrototypeHelper
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.core.screen.MainScreen
import org.saar.lwjgl.opengl.blend.BlendTest
import org.saar.lwjgl.opengl.cullface.CullFace
import org.saar.lwjgl.opengl.depth.DepthTest
import org.saar.lwjgl.opengl.provokingvertex.ProvokingVertex
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.ShadersProgram
import org.saar.lwjgl.opengl.shader.uniforms.*
import org.saar.lwjgl.opengl.stencil.StencilTest

object UILetterRenderer : Renderer<UILetter> {

    private val prototype = LetterRendererPrototype()
    private val helper = RendererPrototypeHelper(this.prototype)

    override fun render(context: RenderContext, models: Iterable<UILetter>) {
        this.helper.render(context, models)
    }

    override fun delete() = this.helper.delete()
}

private class LetterRendererPrototype : RendererPrototype<UILetter> {

    @UniformProperty
    private val resolutionUniform = object : Vec2iUniform() {
        override val name: String = "u_resolution"

        override val value = Vector2i()
            get() = field.set(MainScreen.width, MainScreen.height)
    }

    @UniformProperty
    private val boundsUniform = Vec4UniformValue("u_bounds")

    @UniformProperty
    private val fontColourUniform = UIntUniformValue("u_fontColour")

    @UniformProperty
    private val bitmapUniform = TextureUniformValue("u_bitmap", 0)

    @UniformProperty
    private val bitmapDimensionsUniform = Vec2iUniformValue("u_bitmapDimensions")

    @UniformProperty
    private val bitmapBoundsUniform = Vec4iUniformValue("u_bitmapBounds")

    override val shadersProgram: ShadersProgram = ShadersProgram.create(
        Shader.createVertex(
            GlslVersion.V400,
            ShaderCode.loadSource("/shaders/gui/render/letter.vertex.glsl")
        ),
        Shader.createFragment(
            GlslVersion.V400,
            ShaderCode.loadSource("/shaders/gui/render/letter.fragment.glsl")
        )
    )

    override fun fragmentOutputs() = arrayOf("fragColour")

    override fun onRenderCycle(context: RenderContext) {
        BlendTest.applyAlpha()
        StencilTest.disable()
        DepthTest.disable()
        ProvokingVertex.setFirst()
        CullFace.disable()
    }

    override fun onInstanceDraw(context: RenderContext, model: UILetter) {
        this.bitmapUniform.value = model.font.bitmap

        this.bitmapDimensionsUniform.value = Vector2i(
            model.font.bitmap.width,
            model.font.bitmap.height
        )

        this.bitmapBoundsUniform.value = model.character.bitmapBox.toVector4i()

        val bounds = model.character.localBox.toVector4f()
            .mul(model.style.fontSize.size / model.font.size)
            .add(model.offset.x(), model.offset.y(), 0f, 0f)

        this.boundsUniform.value.set(
            bounds.x() + model.style.position.getX(),
            bounds.y() + model.style.position.getY(),
            bounds.z(),
            bounds.w()
        )

        this.fontColourUniform.value = model.style.fontColour.asInt()
    }

    override fun doInstanceDraw(context: RenderContext, model: UILetter) = QuadMesh.draw()
}
