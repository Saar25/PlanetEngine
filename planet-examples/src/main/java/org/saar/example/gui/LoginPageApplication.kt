package org.saar.example.gui

import org.jproperty.InvalidationListener
import org.saar.core.engine.Application
import org.saar.core.engine.PlanetEngine
import org.saar.core.painting.Painter
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
import org.saar.gui.style.px
import org.saar.lwjgl.glfw.window.Window

fun main() {
    val application = LoginPageApplication()
    val engine = PlanetEngine()
    engine.run(application)
}

class LoginPageApplication : Application {

    private lateinit var background: Painter
    private lateinit var display: UIDisplay

    override fun initialize(window: Window) {
        this.background = FBMPainter()

        this.display = UIDisplay(window).apply {
            this.style.alignment.value = AlignmentValues.vertical
            this.style.arrangement.value = ArrangementValues.spaceEvenly
            this.style.axisAlignment.value = AxisAlignmentValues.center

            +UIElement().apply {
                this.style.padding.set(15.px)
                this.style.borders.bottomValue = 4.px
                this.style.borderColour.set(Colours.BLACK)

                +UIText("Login Page").apply {
                    this.style.fontSize.value = 96.px
                    this.style.fontColour.set(Colours.WHITE)
                }
            }

            val badCredentials = UIText("").apply {
                this.style.fontSize.value = 32.px
                this.style.fontColour.set(Colours.RED)
            }

            val username = UITextField("username")

            +UIElement().apply {
                this.style.fontSize.value = 48.px
                this.style.width.value = 75.percent
                this.style.arrangement.value = ArrangementValues.spaceBetween
                this.style.axisAlignment.value = AxisAlignmentValues.center

                +UIText("Username: ").apply {
                    this.style.fontColour.set(Colours.WHITE)
                }

                +username.apply {
                    this.style.width.value = 350.px
                    this.style.backgroundColour.set(Colours.parse("#e0e0e0"))
                    this.style.padding.set(10.px)

                    this.textProperty.addListener(InvalidationListener { badCredentials.text = "" })
                }
            }

            val password = UITextField("password")

            +UIElement().apply {
                this.style.fontSize.value = 48.px
                this.style.width.value = 75.percent
                this.style.arrangement.value = ArrangementValues.spaceBetween
                this.style.axisAlignment.value = AxisAlignmentValues.center

                +UIText("Password: ").apply {
                    this.style.fontColour.set(Colours.WHITE)
                }

                +password.apply {
                    this.style.width.value = 350.px
                    this.style.backgroundColour.set(Colours.parse("#e0e0e0"))
                    this.style.padding.set(10.px)

                    this.textProperty.addListener(InvalidationListener { badCredentials.text = "" })
                }
            }

            +badCredentials

            +UIButton("Login").apply {
                this.style.fontSize.value = 48.px
                this.style.fontColour.set(Colours.WHITE)
                this.style.borderColour.set(Colours.WHITE)
                this.style.borders.set(1.px)
                this.style.backgroundColour.set(Colours.parse("#212121"))

                this.setOnAction {
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
    }

    override fun update(window: Window) {
        this.display.update()
    }

    override fun render(window: Window) {
        this.background.render()
        this.display.render(RenderContext(null))
    }

    override fun close(window: Window) {
        this.background.delete()
        this.display.delete()
    }
}