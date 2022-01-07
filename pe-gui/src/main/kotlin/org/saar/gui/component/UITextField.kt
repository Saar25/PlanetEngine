package org.saar.gui.component

import org.lwjgl.glfw.GLFW
import org.saar.gui.UIComponent
import org.saar.gui.UITextElement
import org.saar.gui.event.KeyboardEvent
import org.saar.gui.style.Colours

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

class UITextField(text: String = "") : UIComponent() {

    private val uiTextElement = UITextElement().also { it.parent = this }

    override val children = listOf(this.uiTextElement)

    val text: String get() = this.uiTextElement.uiText.text

    init {
        this.uiTextElement.uiText.text = text

        this.style.backgroundColour.set(Colours.WHITE)
        this.style.borderColour.set(Colours.DARK_GRAY)
        this.style.borders.set(2)
    }

    override fun onKeyPress(event: KeyboardEvent) = changeTextByKeyboard(event)

    override fun onKeyRepeat(event: KeyboardEvent) = changeTextByKeyboard(event)

    private fun changeTextByKeyboard(event: KeyboardEvent) {
        val font = this.style.font.value.compute(this.parent.style, this.style)

        this.uiTextElement.uiText.text = when {
            event.keyCode == GLFW.GLFW_KEY_BACKSPACE -> {
                if (event.modifiers.isCtrl()) {
                    this.text.dropLast(this.text.length - this.text.lastIndexOfAny(charArrayOf(' ', '\n')))
                } else {
                    this.text.dropLast(1)
                }
            }
            event.keyCode == GLFW.GLFW_KEY_ENTER -> {
                this.text + '\n'
            }
            font.characters.any { it.char == event.keyCode.toChar() } -> {
                this.text + when {
                    event.modifiers.isShift() -> {
                        characterShiftMap.getOrDefault(
                            event.keyCode, event.keyCode.toChar().uppercaseChar())
                    }
                    event.modifiers.isCapsLock() -> {
                        event.keyCode.toChar().uppercaseChar()
                    }
                    else -> {
                        event.keyCode.toChar().lowercaseChar()
                    }
                }
            }
            else -> {
                this.text
            }
        }
    }
}