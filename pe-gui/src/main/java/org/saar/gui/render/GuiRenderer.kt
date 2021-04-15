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
import org.saar.gui.GuiObject
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.ShaderType
import org.saar.lwjgl.opengl.shaders.uniforms.*
import org.saar.lwjgl.opengl.utils.GlCullFace
import org.saar.lwjgl.opengl.utils.GlUtils

class GuiRenderer(vararg guiObjects: GuiObject) : Renderer,
    RendererPrototypeWrapper<GuiObject>(GuiRendererPrototype(), *guiObjects)

private class GuiRendererPrototype : RendererPrototype<GuiObject> {

    @UniformProperty
    private val windowSizeUniform = object : Vec2iUniform() {
        override fun getName(): String = "u_windowSize"

        override fun getUniformValue() = Vector2i(
            MainScreen.getInstance().width,
            MainScreen.getInstance().height)
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

    @ShaderProperty(ShaderType.VERTEX)
    private val vertex: Shader = Shader.createVertex(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/gui/render/gui.vertex.glsl"))

    @ShaderProperty(ShaderType.FRAGMENT)
    private val fragment: Shader = Shader.createFragment(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/gui/render/gui.fragment.glsl"))

    override fun fragmentOutputs() = arrayOf("fragColour")

    override fun onRenderCycle(context: RenderContext) {
        GlUtils.enableAlphaBlending()
        GlUtils.disableDepthTest()
        GlUtils.setCullFace(GlCullFace.NONE)
    }

    override fun onInstanceDraw(context: RenderContext, guiObject: GuiObject) {
        hasTextureUniform.value = guiObject.texture != null

        boundsUniform.value = Vector4f(guiObject.style.bounds.asVector4i()) // TODO: make these ivec4
        bordersUniform.value = Vector4f(guiObject.style.borders.asVector4i())
        radiusesUniform.value = Vector4f(guiObject.style.radiuses.asVector4i())

        borderColourUniform.value = guiObject.style.borderColour.asInt()
        colourModifierUniform.value = guiObject.style.colourModifier
        cornersColoursUniform.value = guiObject.style.backgroundColour.asVector4i()
        textureUniform.value = guiObject.texture
    }
}
