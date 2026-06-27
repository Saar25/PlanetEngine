package org.saar.example.gui

import org.lwjgl.glfw.GLFW
import org.saar.core.renderer.RenderContext
import org.saar.core.util.Fps
import org.saar.gui.UIDisplay
import org.saar.gui.UIElement
import org.saar.gui.UIText
import org.saar.gui.component.UIButton
import org.saar.gui.event.EventListener
import org.saar.gui.style.Colors
import org.saar.gui.style.length.LengthValues.percent
import org.saar.gui.style.length.LengthValues.ratio
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.lwjgl.opengl.utils.GlUtils

object UIButtonExample {

    private const val WIDTH = 700
    private const val HEIGHT = 500

    @JvmStatic
    fun main(args: Array<String>) {
        val window = Window.create("Lwjgl", WIDTH, HEIGHT, true)

        val uiFps: UIText

        val display = UIDisplay(window) {
            +UIElement {
                style.fontSize.set(48)

                +UIButton {
                    style.width.value = percent(50f)
                    style.height.value = ratio(.5f)
                    onAction = EventListener { println("Clicked!") }
                }

                uiFps = +UIText {
                    style.fontColor.set(Colors.WHITE)
                    style.fontSize.set(22)
                }
            }
        }

        val fps = Fps()

        while (window.isOpen && !window.keyboard.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
            GlUtils.setViewport(0, 0, window.width, window.height)
            GlUtils.clear(GlBuffer.COLOR)

            display.render(RenderContext())

            window.swapBuffers()
            window.pollEvents()

            uiFps.text = "Fps: ${String.format("%.3f", fps.fps())}"
            fps.update()
        }

        display.delete()
        window.destroy()
    }
}