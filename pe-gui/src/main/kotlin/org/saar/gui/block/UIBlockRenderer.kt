package org.saar.gui.block

import org.joml.Vector2i
import org.joml.Vector4i
import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.Renderer
import org.saar.core.renderer.ShadersLink
import org.saar.core.renderer.ShadersUniformsLoader
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.core.screen.MainScreen
import org.saar.gui.UINode
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
import org.saar.lwjgl.opengl.texture.Texture2D

object UIBlockRenderer : Renderer<UINode> {

    private val shadersLink = UIShadersLink
    private val uniformsLoader = ShadersUniformsLoader.from(this.shadersLink)

    override fun render(context: RenderContext, models: Iterable<UINode>) {
        this.shadersLink.shadersProgram.bind()
        BlendTest.applyAlpha()
        StencilTest.disable()
        DepthTest.disable()
        ProvokingVertex.setFirst()
        CullFace.disable()

        models.forEach { model ->
            this.shadersLink.hasTextureUniform.value = model.style.backgroundImage.texture != Texture2D.NULL
            this.shadersLink.textureUniform.value = model.style.backgroundImage.texture

            this.shadersLink.hasDiscardMapUniform.value = model.style.discardMap.texture != Texture2D.NULL
            this.shadersLink.discardMapUniform.value = model.style.discardMap.texture

            // TODO: make these ivec4
            this.shadersLink.boundsUniform.value.set(
                model.style.position.getX().toFloat(),
                model.style.position.getY().toFloat(),
                model.style.width.get().toFloat(),
                model.style.height.get().toFloat()
            )

            val vector4i = Vector4i()
            this.shadersLink.bordersUniform.value.set(model.style.borders.asVector4i(vector4i))
            this.shadersLink.radiusesUniform.value.set(model.style.radius.asVector4i(vector4i))
            this.shadersLink.cornersColoursUniform.value = model.style.backgroundColour.asVector4i(vector4i)
            this.shadersLink.opacityUniform.value = model.style.opacity.opacity

            this.shadersLink.borderColourUniform.value = model.style.borderColour.asInt()
            this.shadersLink.colourModifierUniform.value.set(model.style.colourModifier.multiply)

            this.uniformsLoader.load()
            QuadMesh.draw()
        }
    }

    override fun delete() = this.shadersLink.shadersProgram.delete()

    private object UIShadersLink : ShadersLink {

        @UniformProperty
        val resolutionUniform = object : Vec2iUniform() {
            override val name = "u_resolution"

            // TODO: use bound screen instead of main screen
            override val value = Vector2i()
                get() = field.set(MainScreen.width, MainScreen.height)
        }

        @UniformProperty
        val boundsUniform = Vec4UniformValue("u_bounds")

        @UniformProperty
        val bordersUniform = Vec4UniformValue("u_borders")

        @UniformProperty
        val radiusesUniform = Vec4UniformValue("u_radiuses")

        @UniformProperty
        val opacityUniform = FloatUniformValue("u_opacity")

        @UniformProperty
        val borderColourUniform = UIntUniformValue("u_borderColour")

        @UniformProperty
        val colourModifierUniform = Vec4UniformValue("u_colourModifier")

        @UniformProperty
        val cornersColoursUniform = Vec4iUniformValue("u_cornersColours")

        @UniformProperty
        val hasTextureUniform = BooleanUniformValue("u_hasTexture")

        @UniformProperty
        val textureUniform = TextureUniformValue("u_texture", 0)

        @UniformProperty
        val hasDiscardMapUniform = BooleanUniformValue("u_hasDiscardMap")

        @UniformProperty
        val discardMapUniform = TextureUniformValue("u_discardMap", 1)

        override val fragmentOutputs = arrayOf("fragColour")

        override val shadersProgram: ShadersProgram = ShadersProgram.create(
            Shader.createVertex(GlslVersion.V400, ShaderCode.loadSource("/shaders/gui/render/gui.vertex.glsl")),
            Shader.createFragment(GlslVersion.V400, ShaderCode.loadSource("/shaders/gui/render/gui.fragment.glsl"))
        )
    }
}