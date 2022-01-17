package org.saar.gui.style.margin

import org.jproperty.constant.ConstantIntegerProperty
import org.jproperty.value.ObservableIntegerValue
import org.saar.gui.UIChildNode
import org.saar.gui.style.margin.MarginValues.pixels

class Margin(private val container: UIChildNode) : ReadonlyMargin {

    var topValue: MarginValue = MarginValues.none
        set(value) {
            this.top = value.buildTop(this.container)
            field = value
        }

    var rightValue: MarginValue = MarginValues.none
        set(value) {
            this.right = value.buildRight(this.container)
            field = value
        }

    var bottomValue: MarginValue = MarginValues.none
        set(value) {
            this.bottom = value.buildBottom(this.container)
            field = value
        }

    var leftValue: MarginValue = MarginValues.none
        set(value) {
            this.left = value.buildLeft(this.container)
            field = value
        }

    override var top: ObservableIntegerValue = ConstantIntegerProperty(0)

    override var right: ObservableIntegerValue = ConstantIntegerProperty(0)

    override var bottom: ObservableIntegerValue = ConstantIntegerProperty(0)

    override var left: ObservableIntegerValue = ConstantIntegerProperty(0)

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