package org.saar.example.gui

import org.jproperty.InvalidationListener
import org.lwjgl.glfw.GLFW
import org.saar.core.painting.painters.FBMPainter
import org.saar.core.renderer.RenderContext
import org.saar.gui.UIDisplay
import org.saar.gui.UIElement
import org.saar.gui.UIText
import org.saar.gui.component.UIButton
import org.saar.gui.component.UITextField
import org.saar.gui.style.Colours
import org.saar.gui.style.alignment.AlignmentValues.vertical
import org.saar.gui.style.arrangement.ArrangementValues.spaceBetween
import org.saar.gui.style.arrangement.ArrangementValues.spaceEvenly
import org.saar.gui.style.axisalignment.AxisAlignmentValues
import org.saar.gui.style.percent
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
            style.alignment.value = vertical
            style.arrangement.value = spaceEvenly
            style.axisAlignment.value = AxisAlignmentValues.center

            +UIElement().apply {
                style.padding.set(15.px)
                style.borders.bottomValue = 4.px
                style.borderColour.set(Colours.BLACK)

                +UIText("Login Page").apply {
                    style.fontSize.value = 96.px
                    style.fontColour.set(Colours.WHITE)
                }
            }

            val badCredentials = UIText("").apply {
                style.fontSize.value = 32.px
                style.fontColour.set(Colours.RED)
            }

            val username = UITextField("username")

            +UIElement().apply {
                style.fontSize.value = 48.px
                style.width.value = 75.percent
                style.arrangement.value = spaceBetween
                style.axisAlignment.value = AxisAlignmentValues.center

                +UIText("Username: ").apply {
                    style.fontColour.set(Colours.WHITE)
                }

                +username.apply {
                    style.width.value = 350.px
                    style.backgroundColour.set(Colours.parse("#e0e0e0"))
                    style.padding.set(10.px)

                    textProperty.addListener(InvalidationListener { badCredentials.text = "" })
                }
            }

            val password = UITextField("password")

            +UIElement().apply {
                style.fontSize.value = 48.px
                style.width.value = 75.percent
                style.arrangement.value = spaceBetween
                style.axisAlignment.value = AxisAlignmentValues.center

                +UIText("Password: ").apply {
                    style.fontColour.set(Colours.WHITE)
                }

                +password.apply {
                    style.width.value = 350.px
                    style.backgroundColour.set(Colours.parse("#e0e0e0"))
                    style.padding.set(10.px)

                    textProperty.addListener(InvalidationListener { badCredentials.text = "" })
                }
            }

            +badCredentials

            +UIButton("Login").apply {
                style.fontSize.value = 48.px
                style.fontColour.set(Colours.WHITE)
                style.borderColour.set(Colours.WHITE)
                style.borders.set(1.px)
                style.backgroundColour.set(Colours.parse("#212121"))

                setOnAction {
                    if (username.text == "Ragnar Lothbrok" && password.text == "Odin <3!!") {
                        badCredentials.text = "Noice"
                        badCredentials.style.fontColour.set(Colours.GREEN)
                    } else {
                        badCredentials.text = "Bad username or password!"
                        badCredentials.style.fontColour.set(Colours.RED)
                    }
                }
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