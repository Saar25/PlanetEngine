package org.saar.example.gui

import org.jproperty.Observable
import org.lwjgl.glfw.GLFW
import org.saar.core.renderer.RenderContext
import org.saar.gui.UIDisplay
import org.saar.gui.UIElement
import org.saar.gui.component.UIButton
import org.saar.gui.component.UICheckbox
import org.saar.gui.component.UISlider
import org.saar.gui.event.EventListener
import org.saar.gui.style.alignment.AlignmentValues.vertical
import org.saar.gui.style.arrangement.ArrangementValues.spaceBetween
import org.saar.gui.style.axisalignment.AxisAlignmentValues.center
import org.saar.gui.style.length.LengthValues.fill
import org.saar.gui.style.length.LengthValues.percent
import org.saar.gui.style.length.LengthValues.pixels
import org.saar.gui.style.length.LengthValues.ratio
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.lwjgl.opengl.utils.GlUtils

private const val WIDTH = 700
private const val HEIGHT = 500

fun main() {
    val window = Window.create("Lwjgl", WIDTH, HEIGHT, true)

    val display = UIDisplay(window) {
        style.alignment.value = vertical
        style.arrangement.value = spaceBetween
        style.axisAlignment.value = center

        val sizeUiSlider = +UISlider {
            style.width.value = fill
            style.height.value = pixels(30)
            style.margin.set(20)
        }

        val uiContainer = +UIElement {
            style.width.value = percent(50f)
            style.alignment.value = vertical
            style.axisAlignment.value = center

            +MyUIComponent()

            val uiButton = +UIButton {
                style.width.value = percent(10f)
                style.height.value = ratio(.5f)
                onAction = EventListener { println("Clicked!") }
            }

            +UISlider {
                style.width.value = percent(90f)
                style.height.value = pixels(40)

                dynamicValueProperty.addListener { _: Observable ->
                    val percents = dynamicValueProperty.floatValue / 2
                    uiButton.style.width.value = percent(percents)
                }
            }

            +UICheckbox {
                style.width.value = pixels(30)
            }
        }

        sizeUiSlider.dynamicValueProperty.addListener { _: Observable ->
            val percents = sizeUiSlider.dynamicValueProperty.floatValue / 100 * 50 + 20
            uiContainer.style.width.value = percent(percents)
        }
    }

    while (window.isOpen && !window.keyboard.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
        GlUtils.setViewport(0, 0, window.width, window.height)
        GlUtils.clear(GlBuffer.COLOR)

        display.render(RenderContext())

        window.swapBuffers()
        window.pollEvents()
    }

    display.delete()
    window.destroy()
}