package org.saar.gui

import org.saar.core.renderer.RenderContext
import org.saar.gui.font.Font
import org.saar.gui.font.Letter
import org.saar.gui.font.LetterRenderer
import org.saar.gui.style.Style
import org.saar.maths.utils.Vector2
import kotlin.properties.Delegates

class UIText(private val font: Font, text: String) : UIChildNode, UIElement {

    override var parent: UIElement = UINullElement

    override val style = Style(this)

    var text: String by Delegates.observable(text) { _, _, newValue ->
        this.letters = stringToUiLetters(newValue, this.maxWidth)
    }

    var maxWidth: Int by Delegates.observable(0) { _, _, newValue ->
        this.letters = stringToUiLetters(this.text, newValue)
    }

    private var letters = stringToUiLetters(this.text, this.maxWidth)

    private fun stringToUiLetters(text: String, maxWidth: Int): List<Letter> {
        val advance = Vector2.of(0f, this.font.lineHeight)

        return text.map { char ->
            val character = this.font.getCharacterOrDefault(char)

            if (maxWidth > 0 && advance.x + character.xAdvance > maxWidth) {
                advance.y += this.font.lineHeight
                advance.x = 0f
            }

            Letter(this, this.font, character, Vector2.of(advance)).also {
                advance.add(character.xAdvance, 0f)

                if (char == '\n') {
                    advance.y += this.font.lineHeight
                    advance.x = 0f
                }
            }
        }
    }

    override fun render(context: RenderContext) {
        LetterRenderer.render(context, this.letters)
    }

    override fun delete() = this.letters.forEach { it.delete() }
}