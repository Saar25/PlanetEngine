package org.saar.gui.font

import org.joml.Vector2i
import org.joml.Vector4f
import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.*
import org.saar.core.renderer.shaders.ShaderProperty
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.core.screen.MainScreen
import org.saar.gui.UITextElement
import org.saar.lwjgl.opengl.depth.DepthTest
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.ShaderType
import org.saar.lwjgl.opengl.shaders.uniforms.*
import org.saar.lwjgl.opengl.utils.GlCullFace
import org.saar.lwjgl.opengl.utils.GlUtils

object UILetterRenderer : Renderer, RendererMethodsBase<RenderContext, UILetter> {

    private val prototype = LetterRendererPrototype()
    private val helper = RendererPrototypeHelper(this.prototype)

    override fun render(context: RenderContext, models: Iterable<UILetter>) {
        this.helper.render(context, models)
    }

    fun render(context: RenderContext, uiElement: UITextElement) {
        this.helper.render(context) { uiElement.uiText.render(context) }
    }

    override fun delete() = this.helper.delete()
}

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
        GlUtils.setCullFace(GlCullFace.NONE)
        DepthTest.disable()
    }

    override fun onInstanceDraw(context: RenderContext, uiLetter: UILetter) {
        this.bitmapUniform.value = uiLetter.font.bitmap

        this.bitmapDimensionsUniform.value = Vector2i(
            uiLetter.font.bitmap.width,
            uiLetter.font.bitmap.height)

        this.bitmapBoundsUniform.value = uiLetter.character.bitmapBox.toVector4i()

        val bounds = uiLetter.character.localBox.toVector4f()
            .mul(uiLetter.style.fontSize.get() / uiLetter.font.size)
            .add(uiLetter.offset.x(), uiLetter.offset.y(), 0f, 0f)

        this.boundsUniform.value = Vector4f(
            bounds.x() + uiLetter.style.x.get(),
            bounds.y() + uiLetter.style.y.get(),
            bounds.z(),
            bounds.w())
    }

    override fun doInstanceDraw(context: RenderContext, uiLetter: UILetter) = QuadMesh.draw()
}
