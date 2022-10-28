package org.saar.gui

import org.jproperty.binding.ObjectBinding
import org.jproperty.map
import org.jproperty.property.SimpleIntegerProperty
import org.jproperty.property.SimpleObjectProperty
import org.saar.core.renderer.RenderContext
import org.saar.gui.font.UILetter
import org.saar.gui.font.UILetterRenderer
import org.saar.gui.style.TextStyle
import org.saar.maths.utils.Vector2

class UIText(text: String = "") : UIChildNode {

    constructor() : this("")

    override var parent: UIParentNode = UINullNode

    override val uiInputHelper = UIInputHelper(this)

    override val style = TextStyle(this)

    val textProperty = SimpleObjectProperty(text)

    var text: String
        get() = this.textProperty.value
        set(value) {
            this.textProperty.value = value
        }

    private val maxWidthProperty = SimpleIntegerProperty()

    val lettersProperty = object : ObjectBinding<List<UILetter>>() {
        init {
            bind(this@UIText.textProperty, this@UIText.maxWidthProperty)
        }

        override fun compute() = newLetters(this@UIText.textProperty.value)

        override fun dispose() = unbind(this@UIText.textProperty, this@UIText.maxWidthProperty)
    }

    private val contentWidthProperty = this.lettersProperty.map { letters ->
        val scale = this.style.fontSize.size / this.style.font.family.size
        if (letters.isNotEmpty()) letters.maxOf { it.offset.x() + it.character.xAdvance * scale }.toInt() else 0
    }

    private val contentHeightProperty = this.lettersProperty.map { letters ->
        val default = this.style.font.family.lineHeight * this.style.fontSize.size / this.style.font.family.size
        (if (letters.isNotEmpty()) letters.maxOf { it.offset.y() } else default).toInt()
    }

    val contentWidth: Int get() = this.contentWidthProperty.value

    val contentHeight: Int get() = this.contentHeightProperty.value

    private fun newLetters(text: String): List<UILetter> {
        val font = this.style.font.family
        val fontScale = this.style.fontSize.size / font.size
        val offset = Vector2.of(0f, font.lineHeight * fontScale)
        val maxWidth = this.maxWidthProperty.intValue
        val yAdvance = font.lineHeight * fontScale

        return text.mapNotNull { char ->
            val character = font.getCharacterOrDefault(char)

            val xAdvance = character.xAdvance * fontScale

            if (maxWidth > 0 && offset.x + xAdvance > maxWidth) {
                offset.y += yAdvance
                offset.x = 0f
            }

            UILetter(this, font, character, Vector2.of(offset)).also {
                offset.add(xAdvance, 0f)

                if (char == '\n') {
                    offset.y += yAdvance
                    offset.x = 0f
                }
            }
        }
    }

    override fun update() {
        val max = this.parent.style.width.getMax()
        this.maxWidthProperty.value = max
    }

    override fun render(context: RenderContext) {
        UILetterRenderer.render(context, this.lettersProperty.value)
    }

    override fun contains(x: Int, y: Int): Boolean {
        return x >= this.style.position.getX() &&
                x <= this.style.position.getX() + this.style.width.get() &&
                y >= this.style.position.getY() &&
                y <= this.style.position.getY() + this.style.height.get()
    }
}