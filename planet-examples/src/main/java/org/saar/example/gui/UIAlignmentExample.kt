package org.saar.example.gui

import org.lwjgl.glfw.GLFW
import org.saar.core.renderer.RenderContext
import org.saar.gui.UIBlock
import org.saar.gui.UIDisplay
import org.saar.gui.UIElement
import org.saar.gui.component.UIButton
import org.saar.gui.style.Colours
import org.saar.gui.style.alignment.AlignmentValues
import org.saar.gui.style.arrangement.ArrangementValues
import org.saar.gui.style.axisalignment.AxisAlignmentValues
import org.saar.gui.style.length.LengthValues.percent
import org.saar.gui.style.position.PositionValues
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.lwjgl.opengl.utils.GlUtils
import kotlin.concurrent.thread

object UIAlignmentExample {

    private const val WIDTH = 1200
    private const val HEIGHT = 700

    @JvmStatic
    fun main(args: Array<String>) {
        val window = Window.create("Lwjgl", WIDTH, HEIGHT, true)

        val keyboard = window.keyboard

        val display = UIDisplay(window).apply {
            style.alignment.value = AlignmentValues.vertical
            style.arrangement.value = ArrangementValues.spaceAround
            style.axisAlignment.value = AxisAlignmentValues.center
        }

        val container = UIElement().apply {
            style.backgroundColour.set(Colours.BLUE)
            style.alignment.value = AlignmentValues.horizontal
            style.arrangement.value = ArrangementValues.spaceAround
            style.width.value = percent(100f)

            add(UIButton().apply {
                style.fontSize.set(48)
                setOnAction { println("Clicked!") }
                style.margin.set(5)
                style.radius.set(10)
            })

            add(UIButton().apply {
                style.fontSize.set(48)
                setOnAction { println("Clicked!") }
                style.margin.set(5)
                style.radius.set(10)
            })

            add(UIButton().apply {
                style.fontSize.set(48)
                setOnAction { println("Clicked!") }
                style.margin.set(5)
                style.radius.set(10)
            })
        }
        display.add(container)

        val container2 = UIElement().apply {
            style.backgroundColour.set(Colours.BLUE)
            style.alignment.value = AlignmentValues.horizontal
            style.arrangement.value = ArrangementValues.spaceAround
            style.width.value = percent(100f)

            add(UIBlock().apply {
                style.backgroundColour.set(Colours.BLUE)
                style.position.value = PositionValues.absolute
            })

            add(UIButton().apply {
                style.fontSize.set(48)
                setOnAction { println("Clicked!") }
                style.margin.set(5)
                style.radius.set(10)
            })

            add(UIButton().apply {
                style.fontSize.set(48)
                setOnAction { println("Clicked!") }
                style.margin.set(5)
                style.radius.set(10)
            })

            add(UIButton().apply {
                style.fontSize.set(48)
                setOnAction { println("Clicked!") }
                style.margin.set(5)
                style.radius.set(10)
            })
        }
        display.add(container2)

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