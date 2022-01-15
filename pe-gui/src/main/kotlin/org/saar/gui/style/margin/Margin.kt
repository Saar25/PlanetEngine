package org.saar.gui.style.margin

import org.saar.gui.UIChildNode
import org.saar.gui.style.value.StyleIntValue
import org.saar.gui.style.value.StyleIntValues
import org.saar.gui.style.value.StyleIntValues.pixels

class Margin(private val container: UIChildNode) : ReadonlyMargin {

    var topValue: StyleIntValue = StyleIntValues.none
    var rightValue: StyleIntValue = StyleIntValues.none
    var bottomValue: StyleIntValue = StyleIntValues.none
    var leftValue: StyleIntValue = StyleIntValues.none

    override val top: Int
        get() = this.topValue.compute(
            this.container.parent.style.margin.top)

    override val right: Int
        get() = this.rightValue.compute(
            this.container.parent.style.margin.right)

    override val bottom: Int
        get() = this.bottomValue.compute(
            this.container.parent.style.margin.bottom)

    override val left: Int
        get() = this.leftValue.compute(
            this.container.parent.style.margin.left)

    fun set(top: StyleIntValue, right: StyleIntValue, bottom: StyleIntValue, left: StyleIntValue) {
        this.topValue = top
        this.rightValue = right
        this.bottomValue = bottom
        this.leftValue = left
    }

    fun set(top: Int, right: Int, bottom: Int, left: Int) =
        set(pixels(top), pixels(right), pixels(bottom), pixels(left))

    fun set(all: Int) = set(all, all, all, all)

    fun set(all: StyleIntValue) = set(all, all, all, all)

    fun set(borders: Margin) = set(
        borders.topValue, borders.rightValue,
        borders.bottomValue, borders.leftValue)
}