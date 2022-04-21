package org.saar.gui.component

import org.jproperty.binding.FloatBinding
import org.jproperty.property.SimpleIntegerProperty
import org.saar.gui.UIChildNode
import org.saar.gui.UIElement
import org.saar.gui.UIText
import org.saar.gui.style.Colours
import org.saar.gui.style.coordinate.CoordinateValues
import org.saar.gui.style.length.LengthValue
import org.saar.gui.style.length.LengthValues
import org.saar.gui.style.position.PositionValues
import kotlin.math.max

class UICaret(private val uiText: UIText) : UIElement() {

    private val indexProperty = SimpleIntegerProperty(3)

    var index: Int
        get() = this.indexProperty.intValue
        set(value) {
            this.indexProperty.set(value)
        }

    private val yProperty = object : FloatBinding() {
        init {
            bind(this@UICaret.uiText.lettersProperty, this@UICaret.indexProperty)
        }

        override fun compute(): Float {
            val index = this@UICaret.indexProperty.intValue
            if (index == 0) return 0f

            val style = this@UICaret.style
            val scale = style.fontSize.size / style.font.family.size
            val letter = this@UICaret.uiText.lettersProperty.value[max(index - 1, 0)]
            return letter.offset.y() - style.font.family.lineHeight * scale
        }

        override fun dispose() = unbind(this@UICaret.uiText.lettersProperty, this@UICaret.indexProperty)
    }

    private val xProperty = object : FloatBinding() {
        init {
            bind(this@UICaret.uiText.lettersProperty, this@UICaret.indexProperty)
        }

        override fun compute(): Float {
            val index = this@UICaret.indexProperty.intValue
            if (index == 0) return 0f

            val style = this@UICaret.style
            val scale = style.fontSize.size / style.font.family.size
            val letter = this@UICaret.uiText.lettersProperty.value[max(index - 1, 0)]
            return letter.offset.x() + letter.character.xAdvance * scale
        }

        override fun dispose() = unbind(this@UICaret.uiText.lettersProperty, this@UICaret.indexProperty)
    }

    init {
        this.style.width.value = Flickering()
        this.style.height.value = LengthValues.em(1)
        this.style.backgroundColour.set(Colours.BLACK)
        this.style.position.value = PositionValues.independent
        this.style.x.value = CoordinateValues.from(this.xProperty)
        this.style.y.value = CoordinateValues.from(this.yProperty)
    }

    private class Flickering : LengthValue {
        private fun compute() = if (System.currentTimeMillis() / 750 % 3 == 0L) 0 else 2

        override fun computeAxisX(container: UIChildNode) = compute()

        override fun computeAxisY(container: UIChildNode) = compute()

        override fun computeMinAxisX(container: UIChildNode) = compute()

        override fun computeMinAxisY(container: UIChildNode) = compute()

        override fun computeMaxAxisX(container: UIChildNode) = compute()

        override fun computeMaxAxisY(container: UIChildNode) = compute()
    }
}