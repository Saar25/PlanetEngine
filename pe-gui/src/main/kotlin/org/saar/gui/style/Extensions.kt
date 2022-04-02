package org.saar.gui.style

import org.saar.gui.UIChildNode
import org.saar.gui.style.border.BorderValue
import org.saar.gui.style.coordinate.CoordinateValue
import org.saar.gui.style.fontsize.FontSizeValue
import org.saar.gui.style.fontsize.FontSizeValues
import org.saar.gui.style.length.LengthValue
import org.saar.gui.style.length.LengthValues
import org.saar.gui.style.margin.MarginValue
import org.saar.gui.style.padding.PaddingValue

class Pixels(val value: Int) : CoordinateValue, LengthValue,
    FontSizeValue, BorderValue, MarginValue, PaddingValue {
    override fun compute(container: UIChildNode): Int = this.value
    override fun computeTop(container: UIChildNode): Int = this.value
    override fun computeRight(container: UIChildNode): Int = this.value
    override fun computeBottom(container: UIChildNode): Int = this.value
    override fun computeLeft(container: UIChildNode): Int = this.value
    override fun computeAxisX(container: UIChildNode): Int = this.value
    override fun computeAxisY(container: UIChildNode): Int = this.value
    override fun computeMinAxisX(container: UIChildNode): Int = this.value
    override fun computeMinAxisY(container: UIChildNode): Int = this.value
    override fun computeMaxAxisX(container: UIChildNode): Int = this.value
    override fun computeMaxAxisY(container: UIChildNode): Int = this.value
}

val Int.px get() = Pixels(this)

class Percentage(value: Float) : CoordinateValue, LengthValue, FontSizeValue {

    private val lengthValue = LengthValues.percent(value)
    private val fontSizeValue = FontSizeValues.percent(value)

    override fun compute(container: UIChildNode): Int = this.fontSizeValue.compute(container)
    override fun computeAxisX(container: UIChildNode): Int = this.lengthValue.computeAxisX(container)
    override fun computeAxisY(container: UIChildNode): Int = this.lengthValue.computeAxisY(container)
    override fun computeMinAxisX(container: UIChildNode): Int = this.lengthValue.computeMinAxisX(container)
    override fun computeMinAxisY(container: UIChildNode): Int = this.lengthValue.computeMinAxisY(container)
    override fun computeMaxAxisX(container: UIChildNode): Int = this.lengthValue.computeMaxAxisX(container)
    override fun computeMaxAxisY(container: UIChildNode): Int = this.lengthValue.computeMaxAxisY(container)
}

val Number.percent get() = Percentage(this.toFloat())