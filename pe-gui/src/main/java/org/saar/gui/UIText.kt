package org.saar.gui

import org.saar.core.renderer.RenderContext
import org.saar.gui.font.Font
import org.saar.gui.render.LetterRenderer
import org.saar.gui.style.Style
import kotlin.properties.Delegates

class UIText(private val font: Font, text: String) : UIChildNode, UIElement {

    override var parent: UIElement = UINullElement

    override val style = Style(this)

    var text: String by Delegates.observable(text) { _, _, newValue ->
        this.letters = stringToUiLetters(newValue)
    }

    private var letters = stringToUiLetters(text)

    private fun stringToUiLetters(text: String): List<UILetter> {
        var xAdvance = 0f
        return text.map { char ->
            UILetter(this, this.font, char, xAdvance).also { letter ->
                xAdvance += letter.character.xAdvance
            }
        }
    }

    override fun render(context: RenderContext) {
        LetterRenderer.render(context, this.letters)
    }

    override fun delete() = this.letters.forEach { it.delete() }
}