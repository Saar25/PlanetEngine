package org.saar.example

import org.lwjgl.glfw.GLFW
import org.saar.core.postprocessing.PostProcessingBuffers
import org.saar.core.postprocessing.PostProcessor
import org.saar.core.postprocessing.processors.Swizzle
import org.saar.core.postprocessing.processors.SwizzlePostProcessor
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.p2d.RenderingBuffers2D
import org.saar.core.renderer.renderpass.RenderPassPrototype
import org.saar.core.renderer.renderpass.RenderPassPrototypeWrapper
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.core.screen.MainScreen
import org.saar.core.screen.ScreenPrototype
import org.saar.core.screen.Screens
import org.saar.core.screen.annotations.ScreenImageProperty
import org.saar.core.screen.image.ColourScreenImage
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.glfw.window.WindowHints
import org.saar.lwjgl.opengl.blend.BlendTest
import org.saar.lwjgl.opengl.clear.ClearColour
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.constants.FormatType
import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.fbos.Fbo
import org.saar.lwjgl.opengl.fbos.attachment.ColourAttachment
import org.saar.lwjgl.opengl.fbos.attachment.allocation.SimpleTextureAllocation
import org.saar.lwjgl.opengl.fbos.attachment.buffer.TextureAttachmentBuffer
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.uniforms.IntUniform
import org.saar.lwjgl.opengl.shaders.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.texture.MutableTexture2D
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

    val screenPrototype = MyScreenPrototype()
    val screen = Screens.fromPrototype(screenPrototype, Fbo.create(WIDTH, HEIGHT))

    val painter = MyPostProcessor()
    val swizzle = SwizzlePostProcessor(Swizzle.R, Swizzle.R, Swizzle.R, Swizzle.R)

    val keyboard = window.keyboard

    keyboard.onKeyPress(GLFW.GLFW_KEY_UP).perform { radius = max((radius * 1.03f).toInt(), radius + 1) }
    keyboard.onKeyPress(GLFW.GLFW_KEY_DOWN).perform { radius = (radius * .97f).toInt() }
    keyboard.onKeyRepeat(GLFW.GLFW_KEY_UP).perform { radius = max((radius * 1.03f).toInt(), radius + 1) }
    keyboard.onKeyRepeat(GLFW.GLFW_KEY_DOWN).perform { radius = (radius * .97f).toInt() }

    while (window.isOpen && !keyboard.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
        screen.setAsDraw()
        screen.resizeToMainScreen()
        GlUtils.clear(GlBuffer.COLOUR)

        painter.render(RenderContext(null), screenPrototype.buffers)
        MainScreen.setAsDraw()
        swizzle.render(RenderContext(null), screenPrototype.buffers)

        window.swapBuffers()
        window.pollEvents()
    }

    painter.delete()
    swizzle.delete()
    screen.delete()
    window.destroy()
}

private class MyScreenPrototype : ScreenPrototype {
    val image = MutableTexture2D.create()

    @ScreenImageProperty(draw = true, read = true)
    private val colourImage = ColourScreenImage(ColourAttachment(
        0, TextureAttachmentBuffer(this.image, SimpleTextureAllocation(
            InternalFormat.R8, FormatType.RED, DataType.U_BYTE))
    ))

    val buffers = object : RenderingBuffers2D {
        override val albedo = image
    }
}

private class MyPostProcessor : PostProcessor {

    private val prototype = MyRenderPassPrototype()
    val wrapper = RenderPassPrototypeWrapper(this.prototype)

    override fun render(context: RenderContext, buffers: PostProcessingBuffers) = this.wrapper.render {
        this.prototype.colourTextureUniform.value = buffers.albedo
    }

    override fun delete() = this.wrapper.delete()
}

private class MyRenderPassPrototype : RenderPassPrototype {

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

    override val fragmentShader: Shader = Shader.createFragment(
        GlslVersion.V400,
        ShaderCode.loadSource("/circle.fragment.glsl"),
    )
}