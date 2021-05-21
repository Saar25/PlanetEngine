package org.saar.example.gui

import org.lwjgl.glfw.GLFW
import org.saar.core.renderer.RenderContextBase
import org.saar.gui.UIDisplay
import org.saar.gui.UIText
import org.saar.gui.component.UIButton
import org.saar.gui.font.Font
import org.saar.gui.font.FontLoader
import org.saar.gui.style.value.CoordinateValues.center
import org.saar.gui.style.value.LengthValues.percent
import org.saar.gui.style.value.LengthValues.ratio
import org.saar.lwjgl.glfw.input.keyboard.KeyEvent
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.opengl.utils.GlUtils

object UIButtonExample {

    private const val WIDTH = 700
    private const val HEIGHT = 500

    private val characterShiftMap = mapOf(
        96 to '~', 49 to '!', 50 to '@',
        51 to '#', 52 to '$', 53 to '%',
        54 to '^', 55 to '&', 56 to '*',
        57 to '(', 48 to ')', 45 to '_',
        61 to '+', 91 to '{', 93 to '}',
        92 to '|', 59 to ':', 222 to '\\',
        44 to '<', 46 to '>', 47 to '?',
        32 to ' ', 39 to '"'
    )

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

        val writeable = UIText(font, "write here").apply {
            style.x.set(0)
            style.y.set(font.size.toInt() * 3)
        }
        display.add(writeable)

        val keyboard = window.keyboard

        keyboard.addKeyPressListener { e ->
            writeable.text = changeTextByKeyboard(font, writeable.text, e)
        }

        keyboard.addKeyRepeatListener { e ->
            writeable.text = changeTextByKeyboard(font, writeable.text, e)
        }

        while (window.isOpen && !keyboard.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
            GlUtils.clearColourAndDepthBuffer()
            display.renderForward(RenderContextBase(null))
            window.update(true)
            window.pollEvents()
        }

        font.delete()
        display.delete()
        window.destroy()
    }

    private fun changeTextByKeyboard(font: Font, text: String, e: KeyEvent): String {
        return when {
            e.keyCode == GLFW.GLFW_KEY_BACKSPACE -> {
                if (e.modifiers.isCtrl()) {
                    text.dropLast(text.length - text.lastIndexOf(' '))
                } else {
                    text.dropLast(1)
                }
            }
            font.characters.any { it.char == e.keyCode.toChar() } -> {
                text + when {
                    e.modifiers.isShift() -> {
                        characterShiftMap.getOrDefault(
                            e.keyCode, e.keyCode.toChar().toUpperCase())
                    }
                    e.modifiers.isCapsLock() -> {
                        e.keyCode.toChar().toUpperCase()
                    }
                    else -> {
                        e.keyCode.toChar().toLowerCase()
                    }
                }
            }
            else -> {
                text
            }
        }
    }
}