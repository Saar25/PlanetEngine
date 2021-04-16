package org.saar.gui.style.border

import org.saar.gui.UIChildElement
import org.saar.gui.style.value.StyleIntValue
import org.saar.gui.style.value.StyleIntValues

class StyleBorders(private val container: UIChildElement) : ReadonlyStyleBorders {

    var topValue: StyleIntValue = StyleIntValues.inherit
    var rightValue: StyleIntValue = StyleIntValues.inherit
    var bottomValue: StyleIntValue = StyleIntValues.inherit
    var leftValue: StyleIntValue = StyleIntValues.inherit

    override var top: Int
        get() = this.topValue.compute(this.container.parent.style.borders.top)
        set(value) {
            this.topValue = StyleIntValues.pixels(value)
        }

    override var right: Int
        get() = this.rightValue.compute(this.container.parent.style.borders.right)
        set(value) {
            this.rightValue = StyleIntValues.pixels(value)
        }

    override var bottom: Int
        get() = this.bottomValue.compute(this.container.parent.style.borders.bottom)
        set(value) {
            this.bottomValue = StyleIntValues.pixels(value)
        }

    override var left: Int
        get() = this.leftValue.compute(this.container.parent.style.borders.left)
        set(value) {
            this.leftValue = StyleIntValues.pixels(value)
        }

    fun set(top: Int, right: Int, bottom: Int, left: Int) {
        this.top = top
        this.right = right
        this.bottom = bottom
        this.left = left
    }

    fun set(all: Int) {
        set(all, all, all, all)
    }

    fun set(borders: StyleBorders) {
        this.topValue = borders.topValue
        this.rightValue = borders.rightValue
        this.bottomValue = borders.bottomValue
        this.leftValue = borders.leftValue
    }
}