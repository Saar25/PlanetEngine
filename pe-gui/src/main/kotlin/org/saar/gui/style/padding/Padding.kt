package org.saar.gui.style.padding

import org.saar.gui.UIChildNode
import org.saar.gui.style.padding.PaddingValues.pixels

class Padding(private val container: UIChildNode) : ReadonlyPadding {

    var topValue: PaddingValue = PaddingValues.none

    var rightValue: PaddingValue = PaddingValues.none

    var bottomValue: PaddingValue = PaddingValues.none

    var leftValue: PaddingValue = PaddingValues.none

    override val top get() = this.topValue.computeTop(this.container)

    override val right get() = this.rightValue.computeRight(this.container)

    override val bottom get() = this.bottomValue.computeBottom(this.container)

    override val left get() = this.leftValue.computeLeft(this.container)

    fun set(top: PaddingValue, right: PaddingValue, bottom: PaddingValue, left: PaddingValue) {
        this.topValue = top
        this.rightValue = right
        this.bottomValue = bottom
        this.leftValue = left
    }

    fun set(top: Int, right: Int, bottom: Int, left: Int) =
        set(pixels(top), pixels(right), pixels(bottom), pixels(left))

    fun set(all: Int) = set(all, all, all, all)

    fun set(vertical: Int, horizontal: Int) = set(vertical, horizontal, vertical, horizontal)

    fun set(all: PaddingValue) = set(all, all, all, all)

    fun set(borders: Padding) = set(
        borders.topValue, borders.rightValue,
        borders.bottomValue, borders.leftValue)
}