package org.saar.gui.font

import org.joml.Vector2i
import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.Renderer
import org.saar.core.renderer.ShadersLink
import org.saar.core.renderer.ShadersUniformsLoader
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.core.screen.MainScreen
import org.saar.lwjgl.opengl.blend.BlendTest
import org.saar.lwjgl.opengl.depth.DepthTest
import org.saar.lwjgl.opengl.provokingvertex.ProvokingVertex
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.ShadersProgram
import org.saar.lwjgl.opengl.shader.uniforms.*
import org.saar.lwjgl.opengl.stencil.StencilTest
import org.saar.maths.toVector4f
import org.saar.maths.toVector4i
import org.saar.rhi.opengl.resterization.toOpengl
import org.saar.rhi.resterization.CullMode
import org.saar.rhi.resterization.RasterizationState

object UILetterRenderer : Renderer<RenderContext, UILetter> {

    private val shadersLink = LetterShadersLink
    private val uniformsLoader = ShadersUniformsLoader.from(this.shadersLink)

    private val rasterizationState = RasterizationState(
        cullMode = CullMode.NONE,
    ).toOpengl()

    override fun render(context: RenderContext, models: Iterable<UILetter>) {
        this.shadersLink.shadersProgram.bind()

        BlendTest.applyAlpha()
        StencilTest.disable()
        DepthTest.disable()
        ProvokingVertex.setFirst()
        this.rasterizationState.set()

        this.uniformsLoader.load()

        models.forEach { model ->
            this.shadersLink.bitmapUniform.value = model.font.bitmap

            this.shadersLink.bitmapDimensionsUniform.value = Vector2i(
                model.font.bitmap.width,
                model.font.bitmap.height
            )

            this.shadersLink.bitmapBoundsUniform.value = model.character.bitmapBox.toVector4i()

            val bounds = model.character.localBox.toVector4f()
                .mul(model.style.fontSize.size / model.font.size)
                .add(model.offset.x(), model.offset.y(), 0f, 0f)

            this.shadersLink.boundsUniform.value.set(
                (bounds.x() + model.style.position.getX()).toInt(),
                (bounds.y() + model.style.position.getY()).toInt(),
                bounds.z().toInt(),
                bounds.w().toInt()
            )

            this.shadersLink.fontColorUniform.value = model.style.fontColor.asInt()

            this.uniformsLoader.load()
            QuadMesh.draw()
        }
    }

    override fun delete() = this.shadersLink.shadersProgram.delete()


    private object LetterShadersLink : ShadersLink {

        @UniformProperty
        private val resolutionUniform = object : Vec2iUniform() {
            override val name: String = "u_resolution"

            // TODO: use bound screen instead of main screen
            override val value = Vector2i()
                get() = field.set(MainScreen.width, MainScreen.height)
        }

        @UniformProperty
        val boundsUniform = Vec4iUniformValue("u_bounds")

        @UniformProperty
        val fontColorUniform = UIntUniformValue("u_fontColor")

        @UniformProperty
        val bitmapUniform = TextureUniformValue("u_bitmap", 0)

        @UniformProperty
        val bitmapDimensionsUniform = Vec2iUniformValue("u_bitmapDimensions")

        @UniformProperty
        val bitmapBoundsUniform = Vec4iUniformValue("u_bitmapBounds")

        override val fragmentOutputs = arrayOf("fragColor")

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
    }
}