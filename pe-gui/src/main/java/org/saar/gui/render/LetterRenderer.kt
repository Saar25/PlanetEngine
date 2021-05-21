package org.saar.gui.render

import org.joml.Vector2i
import org.joml.Vector4f
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.Renderer
import org.saar.core.renderer.RendererPrototype
import org.saar.core.renderer.RendererPrototypeWrapper
import org.saar.core.renderer.shaders.ShaderProperty
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.core.screen.MainScreen
import org.saar.gui.UILetter
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.ShaderType
import org.saar.lwjgl.opengl.shaders.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.shaders.uniforms.Vec2iUniform
import org.saar.lwjgl.opengl.shaders.uniforms.Vec2iUniformValue
import org.saar.lwjgl.opengl.shaders.uniforms.Vec4UniformValue
import org.saar.lwjgl.opengl.utils.GlCullFace
import org.saar.lwjgl.opengl.utils.GlUtils

object LetterRenderer : Renderer, RendererPrototypeWrapper<UILetter>(LetterRendererPrototype())

private class LetterRendererPrototype : RendererPrototype<UILetter> {

    @UniformProperty
    private val resolutionUniform = object : Vec2iUniform() {
        override fun getName(): String = "u_resolution"

        override fun getUniformValue() = Vector2i(
            MainScreen.getInstance().width,
            MainScreen.getInstance().height)
    }

    @UniformProperty
    private val boundsUniform = Vec4UniformValue("u_bounds")

    @UniformProperty
    private val bitmapUniform = TextureUniformValue("u_bitmap", 0)

    @UniformProperty
    private val bitmapDimensionsUniform = Vec2iUniformValue("u_bitmapDimensions")

    @UniformProperty
    private val bitmapBoundsUniform = Vec4UniformValue("u_bitmapBounds")

    @ShaderProperty(ShaderType.VERTEX)
    private val vertex: Shader = Shader.createVertex(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/gui/render/letter.vertex.glsl"))

    @ShaderProperty(ShaderType.FRAGMENT)
    private val fragment: Shader = Shader.createFragment(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/gui/render/letter.fragment.glsl"))

    override fun fragmentOutputs() = arrayOf("fragColour")

    override fun onRenderCycle(context: RenderContext) {
        GlUtils.enableAlphaBlending()
        GlUtils.disableDepthTest()
        GlUtils.setCullFace(GlCullFace.NONE)
    }

    override fun onInstanceDraw(context: RenderContext, uiLetter: UILetter) {
        this.bitmapUniform.value = uiLetter.font.bitmap

        this.bitmapDimensionsUniform.value = Vector2i(
            uiLetter.font.bitmap.width,
            uiLetter.font.bitmap.height)

        val advanceText = uiLetter.parent.children.takeWhile { it != uiLetter }
            .sumByDouble { it.character.xAdvance.toDouble() }.toFloat()

        this.bitmapBoundsUniform.value = Vector4f(
            uiLetter.character.bitmapBox.x0.toFloat(),
            uiLetter.character.bitmapBox.y0.toFloat(),
            uiLetter.character.bitmapBox.width.toFloat(),
            uiLetter.character.bitmapBox.height.toFloat())

        val localBox = uiLetter.character.localBox
        this.boundsUniform.value = Vector4f(
            uiLetter.style.x.get() + localBox.x0 + advanceText,
            uiLetter.style.y.get() + localBox.y0 + uiLetter.font.lineHeight,
            localBox.width.toFloat(),
            localBox.height.toFloat())
    }
}
