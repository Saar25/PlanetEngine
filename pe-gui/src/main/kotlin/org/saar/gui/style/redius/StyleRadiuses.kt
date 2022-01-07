package org.saar.gui.style.redius

import org.saar.gui.UIChildNode
import org.saar.gui.style.value.StyleIntValue
import org.saar.gui.style.value.StyleIntValues
import org.saar.maths.utils.Maths.clamp
import kotlin.math.min

class StyleRadiuses(private val container: UIChildNode) : ReadonlyStyleRadiuses {

    var topRightValue: StyleIntValue = StyleIntValues.inherit
    var topLeftValue: StyleIntValue = StyleIntValues.inherit
    var bottomRightValue: StyleIntValue = StyleIntValues.inherit
    var bottomLeftValue: StyleIntValue = StyleIntValues.inherit

    override var topRight: Int
        get() = clampRadius(this.topRightValue.compute(this.container.parent.style.radiuses.topRight))
        set(value) {
            this.topRightValue = StyleIntValues.pixels(value)
        }

    override var topLeft: Int
        get() = clampRadius(this.topLeftValue.compute(this.container.parent.style.radiuses.topLeft))
        set(value) {
            this.topLeftValue = StyleIntValues.pixels(value)
        }

    override var bottomRight: Int
        get() = clampRadius(this.bottomRightValue.compute(this.container.parent.style.radiuses.bottomRight))
        set(value) {
            this.bottomRightValue = StyleIntValues.pixels(value)
        }

    override var bottomLeft: Int
        get() = clampRadius(this.bottomLeftValue.compute(this.container.parent.style.radiuses.bottomLeft))
        set(value) {
            this.bottomLeftValue = StyleIntValues.pixels(value)
        }

    fun set(topRight: Int, topLeft: Int, bottomRight: Int, bottomLeft: Int) {
        this.topRight = topRight
        this.topLeft = topLeft
        this.bottomRight = bottomRight
        this.bottomLeft = bottomLeft
    }

    fun set(all: Int) {
        set(all, all, all, all)
    }

    fun set(borders: StyleRadiuses) {
        this.topRightValue = borders.topRightValue
        this.topLeftValue = borders.topLeftValue
        this.bottomRightValue = borders.bottomRightValue
        this.bottomLeftValue = borders.bottomLeftValue
    }

    private fun clampRadius(value: Int): Int {
        val w: Int = this.container.style.width.get()
        val h: Int = this.container.style.height.get()

        return clamp(value, 0, min(w, h) / 2)
    }
}