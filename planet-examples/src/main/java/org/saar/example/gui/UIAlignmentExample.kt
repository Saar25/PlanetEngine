package org.saar.example.gui

import org.lwjgl.glfw.GLFW
import org.saar.core.renderer.RenderContext
import org.saar.gui.UIContainer
import org.saar.gui.UIDisplay
import org.saar.gui.component.UIButton
import org.saar.gui.font.FontLoader
import org.saar.gui.style.value.LengthValues
import org.saar.gui.style.value.LengthValues.ratio
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.lwjgl.opengl.utils.GlUtils

object UIAlignmentExample {

    private const val WIDTH = 1200
    private const val HEIGHT = 700

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

        container.add(UIButton().apply {
            style.width.value = LengthValues.pixels(200)
            style.height.value = ratio(.5f)
            setOnAction { println("Clicked!") }
        })

        container.add(UIButton().apply {
            style.width.value = LengthValues.pixels(200)
            style.height.value = ratio(.5f)
            setOnAction { println("Clicked!") }
        })

        container.add(UIButton().apply {
            style.width.value = LengthValues.pixels(200)
            style.height.value = ratio(.5f)
            setOnAction { println("Clicked!") }
        })

        while (window.isOpen && !keyboard.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
            container.update()

            GlUtils.clear(GlBuffer.COLOUR)
            container.render(RenderContext(null))

            window.swapBuffers()
            window.pollEvents()
        }

        font.delete()
        container.delete()
        window.destroy()
    }
}