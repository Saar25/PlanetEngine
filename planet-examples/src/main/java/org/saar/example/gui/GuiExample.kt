package org.saar.example.gui

import org.jproperty.Observable
import org.lwjgl.glfw.GLFW
import org.saar.core.renderer.RenderContext
import org.saar.gui.UIDisplay
import org.saar.gui.UIElement
import org.saar.gui.component.UIButton
import org.saar.gui.component.UICheckbox
import org.saar.gui.component.UISlider
import org.saar.gui.style.Colour
import org.saar.gui.style.alignment.AlignmentValues.vertical
import org.saar.gui.style.arrangement.ArrangementValues.spaceBetween
import org.saar.gui.style.axisalignment.AxisAlignmentValues.center
import org.saar.gui.style.length.LengthValues.percent
import org.saar.gui.style.length.LengthValues.pixels
import org.saar.gui.style.length.LengthValues.ratio
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
            style.arrangement.value = spaceBetween

            val sizeUiSlider = UISlider().apply {
                style.width.value = percent(90f)
                style.height.value = pixels(20)
            }
            add(sizeUiSlider)

            val uiContainer = UIElement().apply {
                style.width.value = percent(50f)
                style.alignment.value = vertical
                style.axisAlignment.value = center

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
                        uiButton.style.width.value = percent(percents)
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

        while (window.isOpen && !keyboard.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
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