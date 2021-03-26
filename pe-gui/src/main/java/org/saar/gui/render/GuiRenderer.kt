package org.saar.gui.render

import org.joml.Vector2i
import org.joml.Vector2ic
import org.joml.Vector4f
import org.saar.core.renderer.AbstractRenderer
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.UniformProperty
import org.saar.gui.GuiObject
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.opengl.constants.RenderMode
import org.saar.lwjgl.opengl.objects.vaos.Vao
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.ShadersProgram
import org.saar.lwjgl.opengl.shaders.uniforms.*
import org.saar.lwjgl.opengl.utils.GlCullFace
import org.saar.lwjgl.opengl.utils.GlRendering
import org.saar.lwjgl.opengl.utils.GlUtils

class GuiRenderer(private vararg val renderList: GuiObject) : AbstractRenderer(shadersProgram) {

    @UniformProperty
    private val windowSizeUniform = object : Vec2iUniform() {
        override fun getName(): String = "u_windowSize"

        override fun getUniformValue(): Vector2ic {
            val w = Window.current().width
            val h = Window.current().height
            return Vector2i(w, h)
        }
    }

    @UniformProperty
    private val hasTextureUniform = BooleanUniformValue("u_hasTexture")

    @UniformProperty
    private val boundsUniform = Vec4UniformValue("u_bounds")

    @UniformProperty
    private val bordersUniform = Vec4UniformValue("u_borders")

    @UniformProperty
    private val radiusesUniform = Vec4UniformValue("u_radiuses")

    @UniformProperty
    private val borderColourUniform = UIntUniformValue("u_borderColour")

    @UniformProperty
    private val colourModifierUniform = Vec4UniformValue("u_colourModifier")

    @UniformProperty
    private val cornersColoursUniform = Vec4iUniformValue("u_cornersColours")

    @UniformProperty
    private val textureUniform = TextureUniformValue("u_texture", 0)

    companion object {
        private val vertex: Shader = Shader.createVertex(GlslVersion.V400,
                ShaderCode.loadSource("/shaders/gui/render/gui.vertex.glsl"))

        private val fragment: Shader = Shader.createFragment(GlslVersion.V400,
                ShaderCode.loadSource("/shaders/gui/render/gui.fragment.glsl"))

        private val shadersProgram: ShadersProgram = ShadersProgram.create(vertex, fragment)
    }

    init {
        init()
    }

    override fun preRender(context: RenderContext) {
        GlUtils.enableAlphaBlending()
        GlUtils.disableDepthTest()
        GlUtils.setCullFace(GlCullFace.NONE)
    }

    override fun onRender(context: RenderContext?) {
        Vao.EMPTY.bind()

        for (guiObject in this.renderList) {
            windowSizeUniform.load()

            hasTextureUniform.value = guiObject.texture != null
            hasTextureUniform.load()

            boundsUniform.value = Vector4f(guiObject.style.bounds.asVector4i())
            boundsUniform.load()

            bordersUniform.value = Vector4f(guiObject.style.borders.asVector4i())
            bordersUniform.load()

            radiusesUniform.value = Vector4f(guiObject.style.radiuses.asVector4i())
            radiusesUniform.load()

            borderColourUniform.value = guiObject.style.borderColour.asInt()
            borderColourUniform.load()

            colourModifierUniform.value = guiObject.style.colourModifier
            colourModifierUniform.load()

            cornersColoursUniform.setValue(guiObject.style.backgroundColour.asVector4i())
            cornersColoursUniform.load()

            textureUniform.value = guiObject.texture
            textureUniform.load()

            GlRendering.drawArrays(RenderMode.TRIANGLE_STRIP, 0, 4)
        }
    }

    override fun onDelete() {
        shadersProgram.delete()
    }
}
