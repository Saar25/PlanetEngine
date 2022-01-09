package org.saar.gui.block

import org.joml.Vector2i
import org.joml.Vector4i
import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.Renderer
import org.saar.core.renderer.RendererPrototype
import org.saar.core.renderer.RendererPrototypeHelper
import org.saar.core.renderer.shaders.ShaderProperty
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.core.screen.MainScreen
import org.saar.gui.UIBlock
import org.saar.lwjgl.opengl.blend.BlendTest
import org.saar.lwjgl.opengl.depth.DepthTest
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.uniforms.*
import org.saar.lwjgl.opengl.stencil.StencilTest
import org.saar.lwjgl.opengl.texture.Texture2D

object UIBlockRenderer : Renderer {

    private val prototype = UIRendererPrototype()
    private val helper = RendererPrototypeHelper(this.prototype)

    fun render(context: RenderContext, models: Iterable<UIBlock>) {
        this.helper.render(context, models)
    }

    fun render(context: RenderContext, uiBlock: UIBlock) {
        this.helper.render(context) { doRender(context, uiBlock) }
    }

    private fun doRender(context: RenderContext, uiBlock: UIBlock) {
        this.helper.render(context, uiBlock)
    }

    override fun delete() = this.helper.delete()
}

private class UIRendererPrototype : RendererPrototype<UIBlock> {

    @UniformProperty
    private val resolutionUniform = object : Vec2iUniform() {
        override val name = "u_resolution"

        override val value = Vector2i()
            get() = field.set(MainScreen.width, MainScreen.height)
    }

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
    private val hasTextureUniform = BooleanUniformValue("u_hasTexture")

    @UniformProperty
    private val textureUniform = TextureUniformValue("u_texture", 0)

    @UniformProperty
    private val hasDiscardMapUniform = BooleanUniformValue("u_hasDiscardMap")

    @UniformProperty
    private val discardMapUniform = TextureUniformValue("u_discardMap", 1)

    @ShaderProperty
    private val vertex: Shader = Shader.createVertex(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/gui/render/gui.vertex.glsl"))

    @ShaderProperty
    private val fragment: Shader = Shader.createFragment(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/gui/render/gui.fragment.glsl"))

    override fun fragmentOutputs() = arrayOf("fragColour")

    override fun onRenderCycle(context: RenderContext) {
        BlendTest.applyAlpha()
        StencilTest.disable()
        DepthTest.disable()
    }

    override fun onInstanceDraw(context: RenderContext, uiBlock: UIBlock) {
        hasTextureUniform.value = uiBlock.texture != Texture2D.NULL
        textureUniform.value = uiBlock.texture

        hasDiscardMapUniform.value = uiBlock.discardMap != Texture2D.NULL
        discardMapUniform.value = uiBlock.discardMap

        // TODO: make these ivec4
        boundsUniform.value.set(
            uiBlock.style.position.getX().toFloat(),
            uiBlock.style.position.getY().toFloat(),
            uiBlock.style.width.get().toFloat(),
            uiBlock.style.height.get().toFloat()
        )

        val vector4i = Vector4i()
        bordersUniform.value.set(uiBlock.style.borders.asVector4i(vector4i))
        radiusesUniform.value.set(uiBlock.style.radiuses.asVector4i(vector4i))
        cornersColoursUniform.value = uiBlock.style.backgroundColour.asVector4i(vector4i)

        borderColourUniform.value = uiBlock.style.borderColour.asInt()
        colourModifierUniform.value.set(uiBlock.style.colourModifier.multiply)
    }

    override fun doInstanceDraw(context: RenderContext, uiBlock: UIBlock) = QuadMesh.draw()
}
