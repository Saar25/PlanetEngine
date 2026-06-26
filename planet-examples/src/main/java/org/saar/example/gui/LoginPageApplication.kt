package org.saar.example.gui

import org.jproperty.InvalidationListener
import org.saar.core.common.renderpass.FBMRenderPass
import org.saar.core.engine.Application
import org.saar.core.engine.PlanetEngine
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderPass
import org.saar.gui.UIDisplay
import org.saar.gui.UIElement
import org.saar.gui.UIText
import org.saar.gui.component.UIButton
import org.saar.gui.component.UITextField
import org.saar.gui.event.EventListener
import org.saar.gui.style.Colors
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

    private lateinit var background: RenderPass
    private lateinit var display: UIDisplay

    override fun initialize(window: Window) {
        this.background = FBMRenderPass()

        this.display = UIDisplay(window).apply {
            this.style.alignment.value = AlignmentValues.vertical
            this.style.arrangement.value = ArrangementValues.spaceEvenly
            this.style.axisAlignment.value = AxisAlignmentValues.center

            +UIElement().apply {
                this.style.padding.set(15.px)
                this.style.borders.bottomValue = 4.px
                this.style.borderColor.set(Colors.BLACK)

                +UIText("Login Page").apply {
                    this.style.fontSize.value = 96.px
                    this.style.fontColor.set(Colors.WHITE)
                }
            }

            val badCredentials = UIText("").apply {
                this.style.fontSize.value = 32.px
                this.style.fontColor.set(Colors.RED)
            }

            val username = UITextField("username")

            +UIElement().apply {
                this.style.fontSize.value = 48.px
                this.style.width.value = 75.percent
                this.style.arrangement.value = ArrangementValues.spaceBetween
                this.style.axisAlignment.value = AxisAlignmentValues.center

                +UIText("Username: ").apply {
                    this.style.fontColor.set(Colors.WHITE)
                }

                +username.apply {
                    this.style.width.value = 350.px
                    this.style.backgroundColor.set(Colors.parse("#e0e0e0"))
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
                    this.style.fontColor.set(Colors.WHITE)
                }

                +password.apply {
                    this.style.width.value = 350.px
                    this.style.backgroundColor.set(Colors.parse("#e0e0e0"))
                    this.style.padding.set(10.px)

                    this.textProperty.addListener(InvalidationListener { badCredentials.text = "" })
                }
            }

            +badCredentials

            +UIButton("Login").apply {
                this.style.fontSize.value = 48.px
                this.style.fontColor.set(Colors.WHITE)
                this.style.borderColor.set(Colors.WHITE)
                this.style.borders.set(1.px)
                this.style.backgroundColor.set(Colors.parse("#212121"))

                this.onAction = EventListener {
                    if (username.text == "Ragnar Lothbrok" && password.text == "Odin <3!!") {
                        badCredentials.text = "Noice"
                        badCredentials.style.fontColor.set(Colors.GREEN)
                    } else {
                        badCredentials.text = "Bad username or password!"
                        badCredentials.style.fontColor.set(Colors.RED)
                    }
                }
            }
        }
    }

    override fun update(window: Window) {
        this.display.update()
    }

    override fun render(window: Window) {
        this.background.render(RenderContext())
        this.display.render(RenderContext())
    }

    override fun close(window: Window) {
        this.background.delete()
        this.display.delete()
    }
}