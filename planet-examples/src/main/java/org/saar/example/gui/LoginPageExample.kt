package org.saar.example.gui

import org.lwjgl.glfw.GLFW
import org.saar.core.painting.painters.FBMPainter
import org.saar.core.renderer.RenderContext
import org.saar.gui.UIDisplay
import org.saar.gui.UIElement
import org.saar.gui.UIText
import org.saar.gui.component.UIButton
import org.saar.gui.component.UITextField
import org.saar.gui.style.Colours
import org.saar.gui.style.alignment.AlignmentValues
import org.saar.gui.style.arrangement.ArrangementValues
import org.saar.gui.style.axisalignment.AxisAlignmentValues
import org.saar.gui.style.percent
import org.saar.gui.style.position.PositionValues
import org.saar.gui.style.px
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.opengl.utils.GlBuffer
import org.saar.lwjgl.opengl.utils.GlUtils

object LoginPageExample {

    private const val WIDTH = 1200
    private const val HEIGHT = 700

    @JvmStatic
    fun main(args: Array<String>) {
        val window = Window.create("Lwjgl", WIDTH, HEIGHT, true)

        val display = UIDisplay(window).apply {
            style.alignment.value = AlignmentValues.vertical
            style.arrangement.value = ArrangementValues.spaceEvenly
            style.axisAlignment.value = AxisAlignmentValues.center

            +UIElement().apply {
                style.padding.set(10.px)
                style.borders.leftValue = 4.px
                style.borderColour.set(Colours.BLACK)

                +UIText("Login Page").apply {
                    style.fontSize.value = 48.px
                    style.fontColour.set(Colours.WHITE)
                }
            }

            +UIElement().apply {
                style.fontSize.value = 48.px
                style.width.value = 50.percent

                +UIText("Username: ").apply {
                    style.position.value = PositionValues.absolute
                    style.x.value = (-210).px
                    style.fontColour.set(Colours.WHITE)
                }

                +UITextField("Enter username here").apply {
                    style.height.value = 50.px
                    style.width.value = 100.percent
                    style.backgroundColour.set(Colours.parse("#e0e0e0"))
                }
            }

            +UIElement().apply {
                style.fontSize.value = 48.px
                style.width.value = 50.percent

                +UIText("Password: ").apply {
                    style.position.value = PositionValues.absolute
                    style.x.value = (-210).px
                    style.fontColour.set(Colours.WHITE)
                }

                +UITextField("Enter password here").apply {
                    style.height.value = 50.px
                    style.width.value = 100.percent
                    style.backgroundColour.set(Colours.parse("#e0e0e0"))
                }
            }

            +UIButton("Login").apply {
                style.fontSize.value = 48.px
                style.fontColour.set(Colours.WHITE)
                style.backgroundColour.set(Colours.parse("#030303"))
            }
        }

        val background = FBMPainter()

        val keyboard = window.keyboard

        while (window.isOpen && !keyboard.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
            display.update()

            GlUtils.clear(GlBuffer.COLOUR, GlBuffer.DEPTH)
            background.render()
            display.render(RenderContext(null))

            window.swapBuffers()
            window.pollEvents()
        }

        background.delete()
        display.delete()
        window.destroy()
    }
}