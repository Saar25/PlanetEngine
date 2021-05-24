package org.saar.gui

import org.saar.core.renderer.RenderContext
import org.saar.gui.font.Font
import org.saar.gui.font.FontLoader
import org.saar.gui.font.UILetter
import org.saar.gui.font.UILetterRenderer
import org.saar.gui.style.TextStyle
import org.saar.maths.utils.Vector2
import kotlin.properties.Delegates

class UIText(val font: Font = FontLoader.DEFAULT_FONT, text: String = "") : UIChildNode, UIElement {

    constructor() : this(FontLoader.DEFAULT_FONT, "")

    constructor(font: Font) : this(font, "")

    constructor(text: String) : this(FontLoader.DEFAULT_FONT, text)

    override val style = TextStyle(this)

    val fontScale: Float get() = this.style.fontSize.get() / this.font.size

    override var parent: UIElement by Delegates.observable(UINullElement) { _, _, _ ->
        updateLetters()
    }

    var text: String by Delegates.observable(text) { _, _, _ ->
        updateLetters()
    }

    private var letters = emptyList<UILetter>()

    private fun updateLetters() {
        val maxWidth = this.style.width.getMax()

        val offset = Vector2.of(0f, this.font.lineHeight * this.fontScale)

        var contentWidth = 0f

        this.letters = this.text.mapNotNull { char ->
            val character = this.font.getCharacterOrDefault(char)

            val xAdvance = character.xAdvance * this.fontScale
            val yAdvance = this.font.lineHeight * this.fontScale

            if (maxWidth > 0 && offset.x + xAdvance > maxWidth) {
                offset.y += yAdvance
                offset.x = 0f
            } else if (char != '\n') {
                contentWidth = contentWidth.coerceAtLeast(offset.x + xAdvance)
            }

            UILetter(this, this.font, character, Vector2.of(offset)).also {
                offset.add(xAdvance, 0f)

                if (char == '\n') {
                    offset.y += yAdvance
                    offset.x = 0f
                }
            }
        }

        this.style.contentWidth.set(contentWidth.toInt())
        this.style.contentHeight.set(offset.y.toInt())
    }

    private var textWidth = 0

    private fun validate() {
        val maxWidth = this.style.width.getMax()
        if (this.textWidth != maxWidth) {
            this.textWidth = maxWidth
            updateLetters()
        }
    }

    override fun update() {
        validate()
    }

    override fun render(context: RenderContext) {
        UILetterRenderer.render(context, this.letters)
    }

    override fun delete() = this.letters.forEach { it.delete() }
}