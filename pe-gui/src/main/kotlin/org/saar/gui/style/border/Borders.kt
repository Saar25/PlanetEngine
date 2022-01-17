package org.saar.gui.style.border

import org.jproperty.binding.IntegerWrapper
import org.saar.gui.UIChildNode

class Borders(private val container: UIChildNode) : ReadonlyBorders {

    var topValue: BorderValue = BorderValues.none
        set(value) {
            this.top.set(value.buildTop(this.container))
            field = value
        }

    var rightValue: BorderValue = BorderValues.none
        set(value) {
            this.right.set(value.buildRight(this.container))
            field = value
        }

    var bottomValue: BorderValue = BorderValues.none
        set(value) {
            this.bottom.set(value.buildBottom(this.container))
            field = value
        }

    var leftValue: BorderValue = BorderValues.none
        set(value) {
            this.left.set(value.buildLeft(this.container))
            field = value
        }


    override val top = IntegerWrapper(this.topValue.buildTop(this.container))

    override val right = IntegerWrapper(this.rightValue.buildRight(this.container))

    override val bottom = IntegerWrapper(this.bottomValue.buildBottom(this.container))

    override val left = IntegerWrapper(this.leftValue.buildLeft(this.container))

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