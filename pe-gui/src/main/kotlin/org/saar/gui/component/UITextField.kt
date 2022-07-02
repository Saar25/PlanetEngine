package org.saar.gui.component

import org.lwjgl.glfw.GLFW
import org.saar.gui.UIComponent
import org.saar.gui.UIText
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

private val characterNumLockMap = mapOf(
    GLFW.GLFW_KEY_KP_0 to GLFW.GLFW_KEY_INSERT,
    GLFW.GLFW_KEY_KP_1 to GLFW.GLFW_KEY_END,
    GLFW.GLFW_KEY_KP_2 to GLFW.GLFW_KEY_DOWN,
    GLFW.GLFW_KEY_KP_3 to GLFW.GLFW_KEY_PAGE_DOWN,
    GLFW.GLFW_KEY_KP_4 to GLFW.GLFW_KEY_LEFT,
    GLFW.GLFW_KEY_KP_6 to GLFW.GLFW_KEY_RIGHT,
    GLFW.GLFW_KEY_KP_7 to GLFW.GLFW_KEY_HOME,
    GLFW.GLFW_KEY_KP_8 to GLFW.GLFW_KEY_UP,
    GLFW.GLFW_KEY_KP_9 to GLFW.GLFW_KEY_PAGE_UP,
)

class UITextField(text: String = "") : UIComponent() {

    private val uiText = UIText(text)

    private val uiCaret = UICaret(this.uiText)

    val textProperty by this.uiText::textProperty

    var text: String by this.uiText::text

    override val children = listOf(this.uiText, this.uiCaret).onEach { it.parent = this }

    init {
        this.style.borders.set(2)
        this.style.backgroundColour.set(Colours.WHITE)
        this.style.borderColour.set(Colours.DARK_GRAY)
    }

    override fun update() {
        this.uiCaret.style.backgroundColour.set(Colours.BLACK)
    }

    override fun onKeyPress(event: KeyboardEvent) = changeTextByKeyboard(event)

    override fun onKeyRepeat(event: KeyboardEvent) = changeTextByKeyboard(event)

    private fun changeTextByKeyboard(event: KeyboardEvent) {
        val font = this.style.font.value.compute(this)

        val index = this.uiCaret.index
        val text = this.text.substring(0, index)
        val after = this.text.substring(index)

        val newText = when {
            event.code == GLFW.GLFW_KEY_BACKSPACE -> {
                if (event.modifiers.isCtrl()) {
                    text.dropLastWhile { it == ' ' }.dropLastWhile { it != ' ' }
                } else {
                    text.dropLast(1)
                }
            }
            event.code == GLFW.GLFW_KEY_ENTER -> {
                text + '\n'
            }
            font.characters.any { it.char == event.code.toChar() } -> {
                text + event.key.toChar()
            }
            else -> {
                text
            }
        }

        this.text = newText + after
        this.uiCaret.index = when (event.code) {
            GLFW.GLFW_KEY_PAGE_DOWN -> 0
            GLFW.GLFW_KEY_HOME -> 0
            GLFW.GLFW_KEY_PAGE_UP -> this.text.length
            GLFW.GLFW_KEY_END -> this.text.length
            GLFW.GLFW_KEY_LEFT -> index - 1
            GLFW.GLFW_KEY_RIGHT -> index + 1
            else -> index + (newText.length - text.length)
        }.coerceIn(0, this.text.length)
    }
}