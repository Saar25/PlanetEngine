package org.saar.gui

import org.saar.core.renderer.RenderContext
import org.saar.gui.font.UILetter
import org.saar.gui.font.UILetterRenderer
import org.saar.gui.style.TextStyle
import org.saar.maths.utils.Vector2
import kotlin.properties.Delegates

class UIText(text: String = "") : UIChildNode {

    constructor() : this("")

    override var parent: UIParentNode = UINullNode

    override val style = TextStyle(this)

    private var isValid: Boolean = false

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
        this.maxWidth = this.parent.style.width.getMax()

        val font = this.style.font.family
        val fontScale = this.style.fontSize.size / font.size
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

    override fun update() {
        if (this.maxWidth != this.parent.style.width.getMax()) {
            this.maxWidth = this.parent.style.width.getMax()
            this.isValid = false
        }

        if (!this.isValid) {
            updateLetters()
            this.isValid = true
        }
    }

    override fun render(context: RenderContext) {
        UILetterRenderer.render(context, this.letters)
    }

    override fun contains(x: Int, y: Int): Boolean {
        return x >= this.style.position.getX() &&
                x <= this.style.position.getX() + this.style.width.get() &&
                y >= this.style.position.getY() &&
                y <= this.style.position.getY() + this.style.height.get()
    }
}