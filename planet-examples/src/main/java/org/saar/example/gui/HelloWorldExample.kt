package org.saar.example.gui

import org.lwjgl.glfw.GLFW
import org.saar.core.renderer.RenderContext
import org.saar.gui.UIDisplay
import org.saar.gui.UIText
import org.saar.gui.style.Colors
import org.saar.gui.style.coordinate.CoordinateValues.center
import org.saar.gui.style.position.PositionValues.absolute
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

        val text = UIText("Hello World!").apply {
            style.x.value = center
            style.y.value = center
            style.position.value = absolute
            style.fontSize.set(48)
            style.fontColor.set(Colors.WHITE)
        }
        display.add(text)

        val keyboard = window.keyboard

        while (window.isOpen && !keyboard.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
            display.update()

            GlUtils.clear(GlBuffer.COLOR)
            display.render(RenderContext())

            window.swapBuffers()
            window.pollEvents()
        }

        display.delete()
        window.destroy()
    }
}