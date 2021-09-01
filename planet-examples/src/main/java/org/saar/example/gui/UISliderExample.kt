package org.saar.example.gui

import org.lwjgl.glfw.GLFW
import org.saar.core.renderer.RenderContextBase
import org.saar.gui.UIDisplay
import org.saar.gui.UITextElement
import org.saar.gui.component.UISlider
import org.saar.gui.style.Colours
import org.saar.gui.style.value.CoordinateValues.center
import org.saar.gui.style.value.CoordinateValues.sub
import org.saar.gui.style.value.LengthValues.pixels
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.lwjgl.opengl.utils.GlUtils

object UISliderExample {

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
            style.borderColour.set(Colours.PURPLE)
        }
        display.add(writeable)

        val borderSize = UITextElement("Border size: 0").apply {
            style.x.value = center()
            style.y.value = sub(center(), 200)
            style.fontSize.set(48)
        }
        display.add(borderSize)

        val scrollbar = UISlider().apply {
            style.x.value = center()
            style.y.value = sub(center(), 300)
            style.width.value = pixels(500)
            style.height.value = pixels(50)
            dynamicValueProperty.addListener { e ->
                writeable.style.borders.set(e.newValue.toInt())
                borderSize.uiText.text = "Border size: " + e.newValue.toInt()
            }
        }
        display.add(scrollbar)

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