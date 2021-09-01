package org.saar.example.gui

import org.lwjgl.glfw.GLFW
import org.saar.core.renderer.RenderContextBase
import org.saar.gui.UIDisplay
import org.saar.gui.UITextElement
import org.saar.gui.style.value.CoordinateValues.center
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.lwjgl.opengl.utils.GlUtils

object HelloWorldExample {

    private const val WIDTH = 1200
    private const val HEIGHT = 700

    @JvmStatic
    fun main(args: Array<String>) {
        val window = Window.create("Lwjgl", WIDTH, HEIGHT, true)

        val display = UIDisplay(window)

        val writeable = UITextElement("Hello World!").apply {
            style.x.value = center()
            style.y.value = center()
            style.fontSize.set(48)
        }
        display.add(writeable)

        val keyboard = window.keyboard

        while (window.isOpen && !keyboard.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
            display.update()

            GlUtils.clear(GlBuffer.COLOUR, GlBuffer.DEPTH)
            display.render(RenderContextBase(null))

            window.update(true)
            window.pollEvents()
        }

        display.delete()
        window.destroy()
    }
}