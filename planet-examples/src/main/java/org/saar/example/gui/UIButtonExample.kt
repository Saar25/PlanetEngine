package org.saar.example.gui

import org.saar.core.renderer.RenderContextBase
import org.saar.gui.UIDisplay
import org.saar.gui.UIText
import org.saar.gui.component.UIButton
import org.saar.gui.font.FontLoader
import org.saar.gui.style.value.CoordinateValues.center
import org.saar.gui.style.value.LengthValues.percent
import org.saar.gui.style.value.LengthValues.ratio
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.opengl.utils.GlUtils

object UIButtonExample {

    private const val WIDTH = 700
    private const val HEIGHT = 500

    @JvmStatic
    fun main(args: Array<String>) {
        val window = Window.create("Lwjgl", WIDTH, HEIGHT, true)

        val display = UIDisplay(window)

        val uiButton = UIButton().apply {
            style.x.set(center())
            style.y.set(center())
            style.width.set(percent(50f))
            style.height.set(ratio(.5f))
            setOnAction { println("Clicked!") }
        }
        display.add(uiButton)

        val font = FontLoader.loadFont("C:/Windows/Fonts/arial.ttf", 48f, 512, 512,
            (0x20.toChar()..0x7e.toChar()).joinToString("") + ('א'..'ת').joinToString("")
        )

        val uiText = UIText(font, "Lwjgl!!, some symbols?").apply {
            style.x.set(0)
            style.y.set(0)
        }
        display.add(uiText)

        val uiText2 = UIText(font, "5! = 1 * 2 * 3 * 4 * 5 = 120.").apply {
            style.x.set(0)
            style.y.set(font.size.toInt())
        }
        display.add(uiText2)

        val uiText3 = UIText(font, "משפט זה עשוי להכיל תוכן מגניב".reversed()).apply {
            style.x.set(0)
            style.y.set(font.size.toInt() * 2)
        }
        display.add(uiText3)

        val keyboard = window.keyboard
        while (window.isOpen && !keyboard.isKeyPressed('E'.toInt())) {
            GlUtils.clearColourAndDepthBuffer()
            display.renderForward(RenderContextBase(null))
            window.update(true)
            window.pollEvents()
        }

        font.delete()
        display.delete()
        window.destroy()
    }
}