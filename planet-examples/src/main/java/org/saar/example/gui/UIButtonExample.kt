package org.saar.example.gui

import org.lwjgl.glfw.GLFW
import org.saar.core.renderer.RenderContext
import org.saar.core.util.Fps
import org.saar.gui.UIDisplay
import org.saar.gui.UIElement
import org.saar.gui.UIText
import org.saar.gui.component.UIButton
import org.saar.gui.event.EventListener
import org.saar.gui.font.FontLoader
import org.saar.gui.style.Colours
import org.saar.gui.style.length.LengthValues.percent
import org.saar.gui.style.length.LengthValues.ratio
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.lwjgl.opengl.utils.GlUtils

object UIButtonExample {

    private const val WIDTH = 700
    private const val HEIGHT = 500

    @JvmStatic
    fun main(args: Array<String>) {
        val window = Window.create("Lwjgl", WIDTH, HEIGHT, true)

        val display = UIDisplay(window)

        val font = FontLoader.loadFont(FontLoader.DEFAULT_FONT_FAMILY, 48f, 512, 512,
            (0x20.toChar()..0x7e.toChar()).joinToString("") + ('א'..'ת').joinToString("")
        )

        val container = UIElement().apply {
            style.fontSize.set(48)
        }
        display.add(container)

        val uiButton = UIButton().apply {
            style.width.value = percent(50f)
            style.height.value = ratio(.5f)
            onAction = EventListener { println("Clicked!") }
        }
        container.add(uiButton)

        val uiFps = UIText("").apply {
            style.fontColour.set(Colours.WHITE)
            style.fontSize.set(22)
        }
        container.add(uiFps)

        val fps = Fps()

        val keyboard = window.keyboard

        while (window.isOpen && !keyboard.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
            display.update()

            GlUtils.clear(GlBuffer.COLOUR)
            display.render(RenderContext(null))

            window.swapBuffers()
            window.pollEvents()

            uiFps.text = "Fps: ${String.format("%.3f", fps.fps())}"
            fps.update()
        }

        font.delete()
        display.delete()
        window.destroy()
    }
}