package org.saar.gui.style.border

import org.jproperty.constant.ConstantIntegerProperty
import org.jproperty.value.ObservableIntegerValue
import org.saar.gui.UIChildNode

class StyleBorders(private val container: UIChildNode) : ReadonlyStyleBorders {

    var topValue: StyleBorderValue = StyleBorderValues.none
        set(value) {
            this.top = value.buildTop(this.container)
            field = value
        }

    var rightValue: StyleBorderValue = StyleBorderValues.none
        set(value) {
            this.right = value.buildRight(this.container)
            field = value
        }

    var bottomValue: StyleBorderValue = StyleBorderValues.none
        set(value) {
            this.bottom = value.buildBottom(this.container)
            field = value
        }

    var leftValue: StyleBorderValue = StyleBorderValues.none
        set(value) {
            this.left = value.buildLeft(this.container)
            field = value
        }


    override var top: ObservableIntegerValue = ConstantIntegerProperty(0)

    override var right: ObservableIntegerValue = ConstantIntegerProperty(0)

    override var bottom: ObservableIntegerValue = ConstantIntegerProperty(0)

    override var left: ObservableIntegerValue = ConstantIntegerProperty(0)

    fun set(top: Int, right: Int, bottom: Int, left: Int) {
        this.top = ConstantIntegerProperty(top)
        this.right = ConstantIntegerProperty(right)
        this.bottom = ConstantIntegerProperty(bottom)
        this.left = ConstantIntegerProperty(left)
    }

    fun set(all: Int) {
        set(all, all, all, all)
    }

    fun set(value: StyleBorderValue) {
        this.topValue = value
        this.rightValue = value
        this.bottomValue = value
        this.leftValue = value
    }

    fun set(borders: StyleBorders) {
        this.topValue = borders.topValue
        this.rightValue = borders.rightValue
        this.bottomValue = borders.bottomValue
        this.leftValue = borders.leftValue
    }
}