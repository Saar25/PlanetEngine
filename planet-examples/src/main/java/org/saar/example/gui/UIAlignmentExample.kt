package org.saar.example.gui

import org.lwjgl.glfw.GLFW
import org.saar.core.renderer.RenderContext
import org.saar.gui.UIDisplay
import org.saar.gui.component.UIButton
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

        val display = UIDisplay(window).apply {
        }

        display.add(UIButton().apply {
            style.fontSize.set(98)
            style.width.value = LengthValues.pixels(200)
            style.height.value = ratio(.5f)
            setOnAction { println("Clicked!") }
            style.margin.set(5)
        })

        display.add(UIButton().apply {
            style.fontSize.set(48)
            style.width.value = LengthValues.pixels(200)
            style.height.value = ratio(.5f)
            setOnAction { println("Clicked!") }
            style.margin.set(5)
        })

        display.add(UIButton().apply {
            style.fontSize.set(48)
            style.width.value = LengthValues.pixels(200)
            style.height.value = ratio(.5f)
            setOnAction { println("Clicked!") }
            style.margin.set(5)
        })

        while (window.isOpen && !keyboard.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
            display.update()

            GlUtils.clear(GlBuffer.COLOUR)
            display.render(RenderContext(null))

            window.swapBuffers()
            window.pollEvents()
        }

        display.delete()
        window.destroy()
    }
}