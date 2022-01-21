package org.saar.gui.style.margin

import org.saar.gui.UIChildNode
import org.saar.gui.style.margin.MarginValues.pixels

class Margin(private val container: UIChildNode) : ReadonlyMargin {

    var topValue: MarginValue = MarginValues.none

    var rightValue: MarginValue = MarginValues.none

    var bottomValue: MarginValue = MarginValues.none

    var leftValue: MarginValue = MarginValues.none

    override val top get() = this.topValue.computeTop(this.container)

    override val right get() = this.rightValue.computeRight(this.container)

    override val bottom get() = this.bottomValue.computeBottom(this.container)

    override val left get() = this.leftValue.computeLeft(this.container)

    fun set(top: MarginValue, right: MarginValue, bottom: MarginValue, left: MarginValue) {
        this.topValue = top
        this.rightValue = right
        this.bottomValue = bottom
        this.leftValue = left
    }

    fun set(top: Int, right: Int, bottom: Int, left: Int) =
        set(pixels(top), pixels(right), pixels(bottom), pixels(left))

    fun set(all: Int) = set(all, all, all, all)

    fun set(all: MarginValue) = set(all, all, all, all)

    fun set(borders: Margin) = set(
        borders.topValue, borders.rightValue,
        borders.bottomValue, borders.leftValue)
}