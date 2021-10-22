package org.saar.example.gui

import org.lwjgl.glfw.GLFW
import org.saar.core.renderer.RenderContextBase
import org.saar.core.util.Fps
import org.saar.gui.UIContainer
import org.saar.gui.UIDisplay
import org.saar.gui.UITextElement
import org.saar.gui.component.UIButton
import org.saar.gui.component.UITextField
import org.saar.gui.font.FontLoader
import org.saar.gui.style.Colours
import org.saar.gui.style.value.CoordinateValues.center
import org.saar.gui.style.value.LengthValues.percent
import org.saar.gui.style.value.LengthValues.ratio
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.lwjgl.opengl.utils.GlUtils

object UIButtonExample {

    private const val WIDTH = 700
    private const val HEIGHT = 500

    @JvmStatic
    fun main(args: Array<String>) {
        val window = Window.create("Lwjgl", WIDTH, HEIGHT, true)

        val keyboard = window.keyboard

        val display = UIDisplay(window)

        val font = FontLoader.loadFont(FontLoader.DEFAULT_FONT_FAMILY, 48f, 512, 512,
            (0x20.toChar()..0x7e.toChar()).joinToString("") + ('א'..'ת').joinToString("")
        )

        val container = UIContainer().apply {
            style.fontSize.set(48)
        }
        display.add(container)

        val uiButton = UIButton().apply {
            style.x.value = center()
            style.y.value = center()
            style.width.value = percent(50f)
            style.height.value = ratio(.5f)
            setOnAction { println("Clicked!") }
        }
        container.add(uiButton)

        val text = """
            Lwjgl!!, some symbols?
            5! = 1 * 2 * 3 * 4 * 5 = 120.
            write here
        """.trimIndent()

        val writeable = UITextField(keyboard, text).apply {
            style.x.value = center()
            style.width.value = percent(50f)
            style.height.value = percent(50f)
        }
        container.add(writeable)

        val uiFps = UITextElement("").apply {
            style.fontColour.set(Colours.WHITE)
            style.fontSize.set(22)
        }
        container.add(uiFps)

        val fps = Fps()

        while (window.isOpen && !keyboard.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
            container.update()

            GlUtils.clear(GlBuffer.COLOUR)
            container.render(RenderContextBase(null))

            window.swapBuffers()
            window.pollEvents()

            uiFps.uiText.text = "Fps: ${String.format("%.3f", fps.fps())}"
            fps.update()
        }

        println(writeable.text)

        font.delete()
        container.delete()
        window.destroy()
    }
}