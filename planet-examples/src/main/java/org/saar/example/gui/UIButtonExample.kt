package org.saar.example.gui

import org.lwjgl.glfw.GLFW
import org.saar.core.renderer.RenderContextBase
import org.saar.core.util.Fps
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

        val text = """
            Lwjgl!!, some symbols?
            5! = 1 * 2 * 3 * 4 * 5 = 120.
            ${"משפט זה עשוי להכיל תוכן מגניב".reversed()}
            write here
        """.trimIndent()

        val writeable = UIText(font, text).apply {
            style.x.set(center())
            style.width.set(percent(50f))
        }
        display.add(writeable)

        val uiFps = UIText(font, "").apply {
            fontSize = 22f
        }
        display.add(uiFps)

        val keyboard = window.keyboard

        keyboard.addKeyPressListener { e ->
            writeable.text = changeTextByKeyboard(font, writeable.text, e)
        }

        keyboard.addKeyRepeatListener { e ->
            writeable.text = changeTextByKeyboard(font, writeable.text, e)
        }

        val fps = Fps()

        while (window.isOpen && !keyboard.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
            display.update()

            GlUtils.clearColourAndDepthBuffer()
            display.render(RenderContextBase(null))

            window.update(true)
            window.pollEvents()

            uiFps.text = "Fps: ${String.format("%.3f", fps.fps())}"
            fps.update()
        }

        println(writeable.text)

        font.delete()
        display.delete()
        window.destroy()
    }

    private fun changeTextByKeyboard(font: Font, text: String, e: KeyEvent): String {
        return when {
            e.keyCode == GLFW.GLFW_KEY_BACKSPACE -> {
                if (e.modifiers.isCtrl()) {
                    text.dropLast(text.length - text.lastIndexOfAny(charArrayOf(' ', '\n')))
                } else {
                    text.dropLast(1)
                }
            }
            e.keyCode == GLFW.GLFW_KEY_ENTER -> {
                text + '\n'
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