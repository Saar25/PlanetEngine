package org.saar.example.gui

import org.lwjgl.glfw.GLFW
import org.saar.core.renderer.RenderContext
import org.saar.gui.UIDisplay
import org.saar.gui.UIElement
import org.saar.gui.UIText
import org.saar.gui.component.UISlider
import org.saar.gui.style.Colours
import org.saar.gui.style.value.AlignmentValues
import org.saar.gui.style.value.LengthValues.percent
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

        val display = UIDisplay(window).apply {
            style.alignment.value = AlignmentValues.vertical
        }

        val uiText = UIText("Hello World!").apply {
            style.fontSize.set(48)
            style.fontColour.set(Colours.WHITE)
        }
        display.add(uiText)

        val blockGap = UIElement().apply {
            style.borderColour.set(Colours.PURPLE)
            style.height.value = percent(50f)
        }
        display.add(blockGap)

        val borderSize = UIText("Border size: 0").apply {
            style.fontSize.set(48)
            style.fontColour.set(Colours.WHITE)
        }
        display.add(borderSize)

        val scrollbar = UISlider().apply {
            style.width.value = pixels(500)
            style.height.value = pixels(50)
            dynamicValueProperty.addListener { e ->
                blockGap.style.borders.set(e.newValue.toInt())
                borderSize.text = "Border size: " + e.newValue.toInt()
            }
        }
        display.add(scrollbar)

        val keyboard = window.keyboard

        while (window.isOpen && !keyboard.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
            display.update()

            GlUtils.clear(GlBuffer.COLOUR, GlBuffer.DEPTH)
            display.render(RenderContext(null))

            window.swapBuffers()
            window.pollEvents()
        }

        display.delete()
        window.destroy()
    }
}