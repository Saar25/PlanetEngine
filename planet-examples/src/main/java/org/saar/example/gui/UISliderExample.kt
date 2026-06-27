package org.saar.example.gui

import org.jproperty.Observable
import org.lwjgl.glfw.GLFW
import org.saar.core.renderer.RenderContext
import org.saar.gui.UIBlock
import org.saar.gui.UIDisplay
import org.saar.gui.UIText
import org.saar.gui.component.UISlider
import org.saar.gui.style.Colors
import org.saar.gui.style.alignment.AlignmentValues
import org.saar.gui.style.length.LengthValues.percent
import org.saar.gui.style.length.LengthValues.pixels
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.lwjgl.opengl.utils.GlUtils

object UISliderExample {

    private const val WIDTH = 1200
    private const val HEIGHT = 700

    @JvmStatic
    fun main(args: Array<String>) {
        val window = Window.create("Lwjgl", WIDTH, HEIGHT, true)

        val display = UIDisplay(window) {
            style.alignment.value = AlignmentValues.vertical

            +UIText("Hello World!") {
                style.fontSize.set(48)
                style.fontColor.set(Colors.WHITE)
            }

            val blockGap = +UIBlock {
                style.borderColor.set(Colors.PURPLE)
                style.height.value = percent(50f)
            }

            val borderSize = +UIText("Border size: 0") {
                style.fontSize.set(48)
                style.fontColor.set(Colors.WHITE)
            }

            +UISlider {
                style.width.value = pixels(500)
                style.height.value = pixels(50)
                dynamicValueProperty.addListener { _: Observable ->
                    blockGap.style.borders.set(dynamicValueProperty.intValue)
                    borderSize.text = "Border size: " + dynamicValueProperty.intValue
                }
            }
        }

        while (window.isOpen && !window.keyboard.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
            GlUtils.setViewport(0, 0, display.width, display.height)
            GlUtils.clear(GlBuffer.COLOR)

            display.render(RenderContext())

            window.swapBuffers()
            window.pollEvents()
        }

        display.delete()
        window.destroy()
    }
}