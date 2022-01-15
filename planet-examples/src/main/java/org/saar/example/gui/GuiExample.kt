package org.saar.example.gui

import org.jproperty.Observable
import org.saar.core.renderer.RenderContext
import org.saar.gui.UIDisplay
import org.saar.gui.UIElement
import org.saar.gui.component.UIButton
import org.saar.gui.component.UICheckbox
import org.saar.gui.component.UISlider
import org.saar.gui.style.Colour
import org.saar.gui.style.value.AlignmentValues.vertical
import org.saar.gui.style.value.LengthValues.percent
import org.saar.gui.style.value.LengthValues.pixels
import org.saar.gui.style.value.LengthValues.ratio
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.lwjgl.opengl.utils.GlUtils

object GuiExample {
    private const val WIDTH = 700
    private const val HEIGHT = 500

    @JvmStatic
    fun main(args: Array<String>) {
        val window = Window.create("Lwjgl", WIDTH, HEIGHT, true)

        val display = UIDisplay(window).apply {
            style.alignment.value = vertical

            val sizeUiSlider = UISlider().apply {
                style.width.value = percent(90f)
                style.height.value = pixels(20)
            }
            add(sizeUiSlider)

            val uiContainer = UIElement().apply {
                style.width.value = percent(50f)
                style.height.value = ratio(1f)

                val uiComponent = MyUIComponent().apply {
                }
                add(uiComponent)

                val uiButton = UIButton().apply {
                    style.width.value = percent(10f)
                    style.height.value = ratio(.5f)
                    setOnAction { println("Clicked!") }
                }
                add(uiButton)

                val uiSlider = UISlider().apply {
                    style.width.value = percent(90f)
                    style.height.value = pixels(20)

                    dynamicValueProperty.addListener { _: Observable ->
                        val percents = dynamicValueProperty.floatValue / 2
                        uiButton.style.width.set(percent(percents))
                    }
                }
                add(uiSlider)

                val uiCheckbox = UICheckbox().apply {
                    style.width.value = pixels(20)
                    style.backgroundColour.set(Colour(48, 63, 159, 1f))
                    style.radius.set(3)
                }
                add(uiCheckbox)
            }
            add(uiContainer)

            sizeUiSlider.dynamicValueProperty.addListener { _: Observable ->
                val percents = sizeUiSlider.dynamicValueProperty.floatValue / 100 * 50 + 20
                uiContainer.style.width.value = percent(percents)
            }
        }

        val keyboard = window.keyboard

        while (window.isOpen && !keyboard.isKeyPressed('E'.code)) {
            GlUtils.clear(GlBuffer.COLOUR)

            display.update()

            display.render(RenderContext(null))

            window.swapBuffers()
            window.pollEvents()
        }

        display.delete()
        window.destroy()
    }
}