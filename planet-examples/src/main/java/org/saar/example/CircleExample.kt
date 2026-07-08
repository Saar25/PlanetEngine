package org.saar.example

import org.lwjgl.glfw.GLFW
import org.saar.core.common.renderpass.Swizzle
import org.saar.core.common.renderpass.SwizzlePostProcessor
import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderPass
import org.saar.core.screen.MainScreen
import org.saar.core.screen.ScreenPrototype
import org.saar.core.screen.Screens.toScreen
import org.saar.core.screen.resizeToMainScreen
import org.saar.core.util.Time
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.glfw.window.WindowHints
import org.saar.lwjgl.opengl.clear.ClearColor
import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.opengl.fbo.Fbo
import org.saar.lwjgl.opengl.fbo.attachment.buffer.TextureAttachmentBuffer
import org.saar.lwjgl.opengl.fbo.attachment.index.ColorAttachmentIndex
import org.saar.lwjgl.opengl.shader.uniforms.IntUniform
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.texture.MutableTexture2D
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.lwjgl.opengl.utils.GlUtils
import org.saar.rhi.opengl.shader.GlslVersion
import org.saar.rhi.opengl.shader.toOpengl
import org.saar.rhi.shader.*
import java.awt.Toolkit
import kotlin.math.max
import kotlin.properties.Delegates

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

//    BlendTest.enable()
//    BlendState(attachment = BlendAttachmentState(blendEnable = true)).toOpengl().set()
    ClearColor.set(0f, 0f, 0f, 0f)

    val screenPrototype = MyScreenPrototype()
    val screen = screenPrototype.toScreen(Fbo.create(), WIDTH, HEIGHT)

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
        GlUtils.clear(GlBuffer.COLOR)

        painter.render(RenderContext())
        MainScreen.setAsDraw()
        swizzle.render(RenderContext())

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

    private val shadersLink = MyShadersLink
    private val uniforms = this.shadersLink.uniforms.associateWith {
        this.shadersLink.shadersProgram.getUniformLocation(it.name)
    }

    override fun render(context: RenderContext) {
        this.shadersLink.shadersProgram.bind()
        this.shadersLink.colorTextureUniform.value = this.albedoBuffer
        this.uniforms.entries.forEach { (uniform, location) -> uniform.load(location) }
        QuadMesh.draw()
    }

    override fun delete() = this.shadersLink.shadersProgram.delete()

    private object MyShadersLink {

        val colorTextureUniform = TextureUniformValue("u_colorTexture", 0)

        val timeUniform = object : IntUniform() {
            private val start = Time()

            override val value get() = this.start.delta().toMillis().toInt()

            override val name = "u_time"
        }

        val radiusUniform = object : IntUniform() {
            override val value get() = radius

            override val name = "u_radius"
        }

        val uniforms = listOf(
            this.colorTextureUniform,
            this.timeUniform,
            this.radiusUniform,
        )

        val shadersProgram = ShaderProgram(
            ShaderStage(
                module = ShaderModule.fromString(
                    GlslVersion.V400.toString() + ShaderModuleLoader.loadSource("/shaders/common/quad/quad.vertex.glsl")
                ),
                type = ShaderStageType.VERTEX,
            ),
            ShaderStage(
                module = ShaderModule.fromString(
                    GlslVersion.V400.toString() + ShaderModuleLoader.loadSource("/circle.fragment.glsl")
                ),
                type = ShaderStageType.FRAGMENT,
            )
        ).toOpengl()
    }
}