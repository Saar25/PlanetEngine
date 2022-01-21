package org.saar.gui.style.border

import org.saar.gui.UIChildNode

class Borders(private val container: UIChildNode) : ReadonlyBorders {

    var topValue: BorderValue = BorderValues.none

    var rightValue: BorderValue = BorderValues.none

    var bottomValue: BorderValue = BorderValues.none

    var leftValue: BorderValue = BorderValues.none

    override val top get() = this.topValue.computeTop(this.container)

    override val right get() = this.rightValue.computeRight(this.container)

    override val bottom get() = this.bottomValue.computeBottom(this.container)

    override val left get() = this.leftValue.computeLeft(this.container)

    fun set(top: Int, right: Int, bottom: Int, left: Int) {
        this.topValue = BorderValues.pixels(top)
        this.rightValue = BorderValues.pixels(right)
        this.bottomValue = BorderValues.pixels(bottom)
        this.leftValue = BorderValues.pixels(left)
    }

    fun set(all: Int) {
        set(all, all, all, all)
    }

    fun set(value: BorderValue) {
        this.topValue = value
        this.rightValue = value
        this.bottomValue = value
        this.leftValue = value
    }

    fun set(borders: Borders) {
        this.topValue = borders.topValue
        this.rightValue = borders.rightValue
        this.bottomValue = borders.bottomValue
        this.leftValue = borders.leftValue
    }
}