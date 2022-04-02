package org.saar.example.gui

import org.lwjgl.glfw.GLFW
import org.saar.core.painting.painters.FBMPainter
import org.saar.core.renderer.RenderContext
import org.saar.gui.UIBlock
import org.saar.gui.UIDisplay
import org.saar.gui.UIElement
import org.saar.gui.UIText
import org.saar.gui.component.UIButton
import org.saar.gui.component.UITextField
import org.saar.gui.style.Colours
import org.saar.gui.style.alignment.AlignmentValues
import org.saar.gui.style.arrangement.ArrangementValues
import org.saar.gui.style.axisalignment.AxisAlignmentValues
import org.saar.gui.style.border.BorderValues
import org.saar.gui.style.coordinate.CoordinateValues
import org.saar.gui.style.length.LengthValues.fill
import org.saar.gui.style.length.LengthValues.percent
import org.saar.gui.style.length.LengthValues.pixels
import org.saar.gui.style.position.PositionValues
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

            val uiTitleContainer = UIElement().apply {
                val uiText = UIText("Login Page").apply {
                    style.fontSize.set(48)
                    style.fontColour.set(Colours.WHITE)
                }
                add(uiText)

                val uiBlock = UIBlock().apply {
                    style.position.value = PositionValues.absolute
                    style.borders.leftValue = BorderValues.pixels(4)
                    style.borderColour.set(Colours.BLACK)
                    style.margin.set(-5)
                }
                add(uiBlock)
            }
            add(uiTitleContainer)

            val uiUsernameContainer = UIElement().apply {
                style.fontSize.set(48)
                style.width.value = percent(50f)

                val uiUsernameTitle = UIText("Username: ").apply {
                    style.position.value = PositionValues.absolute
                    style.x.value = CoordinateValues.pixels(-210)
                    style.fontColour.set(Colours.WHITE)
                }
                add(uiUsernameTitle)

                val uiUsername = UITextField("Enter username here").apply {
                    style.height.value = pixels(50)
                    style.width.value = percent(100f)
                    style.backgroundColour.set(Colours.parse("#e0e0e0"))
                }
                add(uiUsername)
            }
            add(uiUsernameContainer)

            val uiPasswordContainer = UIElement().apply {
                style.fontSize.set(48)
                style.width.value = percent(50f)

                val uiPasswordTitle = UIText("Password: ").apply {
                    style.position.value = PositionValues.absolute
                    style.x.value = CoordinateValues.pixels(-210)
                    style.fontColour.set(Colours.WHITE)
                }
                add(uiPasswordTitle)

                val uiPassword = UITextField("Enter password here").apply {
                    style.height.value = pixels(50)
                    style.width.value = percent(100f)
                    style.backgroundColour.set(Colours.parse("#e0e0e0"))
                }
                add(uiPassword)
            }
            add(uiPasswordContainer)

            val uiLogin = UIButton().apply {
                uiText.text = "Login"
                style.fontSize.set(48)
                style.fontColour.set(Colours.WHITE)
                style.backgroundColour.set(Colours.parse("#030303"))

                setOnAction {
                    //                    println("Login successfully \"${uiUsername.text}\" \"${uiPassword.text}\"")
                }
            }
            add(uiLogin)
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