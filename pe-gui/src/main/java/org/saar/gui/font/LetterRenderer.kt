package org.saar.gui.font

import org.joml.Vector2i
import org.joml.Vector4i
import org.saar.core.renderer.*
import org.saar.core.renderer.shaders.ShaderProperty
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.core.screen.MainScreen
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.ShaderType
import org.saar.lwjgl.opengl.shaders.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.shaders.uniforms.Vec2iUniform
import org.saar.lwjgl.opengl.shaders.uniforms.Vec2iUniformValue
import org.saar.lwjgl.opengl.shaders.uniforms.Vec4iUniformValue
import org.saar.lwjgl.opengl.utils.GlCullFace
import org.saar.lwjgl.opengl.utils.GlUtils

object LetterRenderer : Renderer, RendererMethodsBase<RenderContext, Letter> {

    private val prototype = LetterRendererPrototype()
    private val helper = RendererPrototypeHelper(this.prototype)

    override fun render(context: RenderContext, models: Iterable<Letter>) {
        this.helper.render(context, models)
    }

    override fun delete() = this.helper.delete()
}

private class LetterRendererPrototype : RendererPrototype<Letter> {

    @UniformProperty
    private val resolutionUniform = object : Vec2iUniform() {
        override fun getName(): String = "u_resolution"

        override fun getUniformValue() = Vector2i(
            MainScreen.getInstance().width,
            MainScreen.getInstance().height)
    }

    @UniformProperty
    private val boundsUniform = Vec4iUniformValue("u_bounds")

    @UniformProperty
    private val bitmapUniform = TextureUniformValue("u_bitmap", 0)

    @UniformProperty
    private val bitmapDimensionsUniform = Vec2iUniformValue("u_bitmapDimensions")

    @UniformProperty
    private val bitmapBoundsUniform = Vec4iUniformValue("u_bitmapBounds")

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

    override fun onInstanceDraw(context: RenderContext, letter: Letter) {
        this.bitmapUniform.value = letter.font.bitmap

        this.bitmapDimensionsUniform.value = Vector2i(
            letter.font.bitmap.width,
            letter.font.bitmap.height)

        this.bitmapBoundsUniform.value = Vector4i(
            letter.character.bitmapBox.x0,
            letter.character.bitmapBox.y0,
            letter.character.bitmapBox.width,
            letter.character.bitmapBox.height)

        val localBox = letter.character.localBox
        this.boundsUniform.value = Vector4i(
            letter.style.x.get() + localBox.x0 + letter.xAdvance.toInt(),
            letter.style.y.get() + localBox.y0 + letter.font.lineHeight.toInt(),
            localBox.width,
            localBox.height)
    }
}
