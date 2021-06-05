package org.saar.gui

import org.saar.core.renderer.RenderContext
import org.saar.gui.font.UILetter
import org.saar.gui.font.UILetterRenderer
import org.saar.gui.style.TextStyle
import org.saar.maths.utils.Vector2
import kotlin.properties.Delegates

class UIText(val parent: UITextElement, text: String) {

    private var isValid: Boolean = false

    val style: TextStyle get() = this.parent.style

    var contentWidth: Int = 0
        private set

    var contentHeight: Int = 0
        private set

    private var maxWidth: Int = 0

    var text: String by Delegates.observable(text) { _, _, _ ->
        this.isValid = false
    }

    private var letters = emptyList<UILetter>()

    private fun updateLetters() {
        this.maxWidth = this.parent.parent.style.width.get()

        val font = this.style.font.get()
        val fontScale = this.style.fontSize.get() / font.size
        val offset = Vector2.of(0f, font.lineHeight * fontScale)

        var contentWidth = 0f

        this.letters = this.text.mapNotNull { char ->
            val character = font.getCharacterOrDefault(char)

            val xAdvance = character.xAdvance * fontScale
            val yAdvance = font.lineHeight * fontScale

            if (this.maxWidth > 0 && offset.x + xAdvance > this.maxWidth) {
                offset.y += yAdvance
                offset.x = 0f
            } else if (char != '\n') {
                contentWidth = contentWidth.coerceAtLeast(offset.x + xAdvance)
            }

            UILetter(this, font, character, Vector2.of(offset)).also {
                offset.add(xAdvance, 0f)

                if (char == '\n') {
                    offset.y += yAdvance
                    offset.x = 0f
                }
            }
        }

        this.contentWidth = contentWidth.toInt()
        this.contentHeight = offset.y.toInt()
    }

    fun update() {
        if (this.maxWidth != this.parent.parent.style.width.get()) {
            this.maxWidth = this.parent.parent.style.width.get()
            this.isValid = false
        }

        if (!this.isValid) {
            updateLetters()
            this.isValid = true
        }
    }

    fun render(context: RenderContext) {
        UILetterRenderer.render(context, this.letters)
    }
}