package org.saar.example.gui

import org.lwjgl.glfw.GLFW
import org.saar.core.renderer.RenderContext
import org.saar.gui.UIBlock
import org.saar.gui.UIDisplay
import org.saar.gui.UIElement
import org.saar.gui.component.UIButton
import org.saar.gui.event.EventListener
import org.saar.gui.style.Colors
import org.saar.gui.style.alignment.AlignmentValues
import org.saar.gui.style.arrangement.ArrangementValues
import org.saar.gui.style.axisalignment.AxisAlignmentValues
import org.saar.gui.style.length.LengthValues.percent
import org.saar.gui.style.position.PositionValues
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.lwjgl.opengl.utils.GlUtils

object UIAlignmentExample {

    private const val WIDTH = 1200
    private const val HEIGHT = 700

    @JvmStatic
    fun main(args: Array<String>) {
        val window = Window.create("Lwjgl", WIDTH, HEIGHT, true)

        val display = UIDisplay(window) {
            style.alignment.value = AlignmentValues.vertical
            style.arrangement.value = ArrangementValues.spaceAround
            style.axisAlignment.value = AxisAlignmentValues.center

            +UIElement {
                style.backgroundColor.set(Colors.BLUE)
                style.alignment.value = AlignmentValues.horizontal
                style.arrangement.value = ArrangementValues.spaceAround
                style.width.value = percent(100f)

                +UIButton {
                    style.fontSize.set(48)
                    onAction = EventListener { println("Clicked!") }
                    style.margin.set(5)
                    style.radius.set(10)
                }

                +UIButton {
                    style.fontSize.set(48)
                    onAction = EventListener { println("Clicked!") }
                    style.margin.set(5)
                    style.radius.set(10)
                }

                +UIButton {
                    style.fontSize.set(48)
                    onAction = EventListener { println("Clicked!") }
                    style.margin.set(5)
                    style.radius.set(10)
                }
            }

            +UIElement {
                style.backgroundColor.set(Colors.BLUE)
                style.alignment.value = AlignmentValues.horizontal
                style.arrangement.value = ArrangementValues.spaceEvenly
                style.width.value = percent(100f)

                +UIBlock {
                    style.backgroundColor.set(Colors.BLUE)
                    style.position.value = PositionValues.absolute
                }

                +UIButton {
                    style.fontSize.set(48)
                    onAction = EventListener { println("Clicked!") }
                    style.margin.set(5)
                    style.radius.set(10)
                }

                +UIButton {
                    style.fontSize.set(48)
                    onAction = EventListener { println("Clicked!") }
                    style.margin.set(5)
                    style.radius.set(10)
                }

                +UIButton {
                    style.fontSize.set(48)
                    onAction = EventListener { println("Clicked!") }
                    style.margin.set(5)
                    style.radius.set(10)
                }
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
}