package org.saar.example

import org.lwjgl.glfw.GLFW
import org.saar.core.mesh.common.QuadMesh
import org.saar.core.postprocessing.Swizzle
import org.saar.core.postprocessing.SwizzlePostProcessor
import org.saar.core.renderer.*
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.core.screen.MainScreen
import org.saar.core.screen.ScreenPrototype
import org.saar.core.screen.Screens.toScreen
import org.saar.core.screen.resizeToMainScreen
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.glfw.window.WindowHints
import org.saar.lwjgl.opengl.blend.BlendTest
import org.saar.lwjgl.opengl.clear.ClearColour
import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.fbo.Fbo
import org.saar.lwjgl.opengl.fbo.attachment.allocation.SimpleAllocationStrategy
import org.saar.lwjgl.opengl.fbo.attachment.buffer.TextureAttachmentBuffer
import org.saar.lwjgl.opengl.fbo.attachment.index.ColorAttachmentIndex
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.ShadersProgram
import org.saar.lwjgl.opengl.shader.uniforms.IntUniform
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.texture.MutableTexture2D
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.lwjgl.opengl.utils.GlUtils
import java.awt.Dimension
import java.awt.Toolkit
import kotlin.math.max
import kotlin.properties.Delegates

private operator fun Dimension.component1() = this.width
private operator fun Dimension.component2() = this.height

private val WIDTH = Toolkit.getDefaultToolkit().screenSize.width
private val HEIGHT = Toolkit.getDefaultToolkit().screenSize.height

private var radius: Int by Delegates.vetoable(100) { _, _, newValue ->
    newValue > 0 && newValue < WIDTH / 2 && newValue < HEIGHT / 2
}

fun main() {
    val window = Window.builder("Lwjgl", WIDTH, HEIGHT, true)
        .hint(WindowHints.decorated(false))
        .hint(WindowHints.transparent())
        .hint(WindowHints.focused())
        .build()

    BlendTest.enable()
    ClearColour.set(0f, 0f, 0f, 0f)

    val fbo = Fbo.create(WIDTH, HEIGHT)
    val screenPrototype = MyScreenPrototype()
    val allocation = SimpleAllocationStrategy
    val screen = screenPrototype.toScreen(fbo, allocation)

    val painter = MyPostProcessor(screenPrototype.albedoTexture)
    val swizzle = SwizzlePostProcessor(screenPrototype.albedoTexture, Swizzle.R, Swizzle.R, Swizzle.R, Swizzle.R)

    val keyboard = window.keyboard

    keyboard.onKeyPress(GLFW.GLFW_KEY_UP).perform { radius = max((radius * 1.03f).toInt(), radius + 1) }
    keyboard.onKeyPress(GLFW.GLFW_KEY_DOWN).perform { radius = (radius * .97f).toInt() }
    keyboard.onKeyRepeat(GLFW.GLFW_KEY_UP).perform { radius = max((radius * 1.03f).toInt(), radius + 1) }
    keyboard.onKeyRepeat(GLFW.GLFW_KEY_DOWN).perform { radius = (radius * .97f).toInt() }

    while (window.isOpen && !keyboard.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
        screen.setAsDraw()
        screen.resizeToMainScreen()
        GlUtils.clear(GlBuffer.COLOUR)

        painter.render(RenderContext(null))
        MainScreen.setAsDraw()
        swizzle.render(RenderContext(null))

        window.swapBuffers()
        window.pollEvents()
    }

    painter.delete()
    swizzle.delete()
    screen.delete()
    window.destroy()
}

private class MyScreenPrototype : ScreenPrototype {
    val albedoTexture: MutableTexture2D = MutableTexture2D.create()

    override val colorBuffers = listOf(
        TextureAttachmentBuffer(this.albedoTexture, InternalFormat.R8)
    )

    override val readIndex = ColorAttachmentIndex.at(0)
}

private class MyPostProcessor(private val albedoBuffer: ReadOnlyTexture2D) : RenderPass {

    private val prototype = MyRenderPassPrototype()
    val wrapper = RendererPrototypeHelper(this.prototype)

    override fun render(context: RenderContext) = this.wrapper.render(context) {
        this.prototype.colourTextureUniform.value = this.albedoBuffer
    }

    override fun delete() = this.wrapper.delete()
}

private class MyRenderPassPrototype : RendererPrototype<Unit> {

    @UniformProperty
    val colourTextureUniform = TextureUniformValue("u_colourTexture", 0)

    @UniformProperty
    val timeUniform = object : IntUniform() {
        private val start = System.currentTimeMillis()
        override val value get() = (System.currentTimeMillis() - this.start).toInt()

        override val name = "u_time"
    }

    @UniformProperty
    val radiusUniform = object : IntUniform() {
        override val value get() = radius

        override val name = "u_radius"
    }

    override val shadersProgram: ShadersProgram = ShadersProgram.create(
        Shader.createVertex(GlslVersion.V400, Renderers.quadVertexShaderCode),
        Shader.createFragment(GlslVersion.V400, ShaderCode.loadSource("/circle.fragment.glsl")),
    )

    override fun doInstanceDraw(context: RenderContext, model: Unit) = QuadMesh.draw()
}