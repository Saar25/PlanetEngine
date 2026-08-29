package org.saar.gui.style.backgroundcolor

import org.saar.gui.UIChildNode
import org.saar.gui.style.Color

class BackgroundColor(
    private val container: UIChildNode,
    default: BackgroundColorValue = BackgroundColorValues.inherit,
) : ReadonlyBackgroundColor {

    var topRightValue: BackgroundColorValue = default

    var topLeftValue: BackgroundColorValue = default

    var bottomRightValue: BackgroundColorValue = default

    var bottomLeftValue: BackgroundColorValue = default

    override val topRight get() = this.topRightValue.computeTopRight(this.container)

    override val topLeft get() = this.topLeftValue.computeTopLeft(this.container)

    override val bottomRight get() = this.bottomRightValue.computeBottomRight(this.container)

    override val bottomLeft get() = this.bottomLeftValue.computeBottomLeft(this.container)

    fun set(color: Color) {
        this.topRightValue = BackgroundColorValues.of(color)
        this.topLeftValue = BackgroundColorValues.of(color)
        this.bottomRightValue = BackgroundColorValues.of(color)
        this.bottomLeftValue = BackgroundColorValues.of(color)
    }

    fun set(colorValue: BackgroundColorValue) {
        this.topRightValue = colorValue
        this.topLeftValue = colorValue
        this.bottomRightValue = colorValue
        this.bottomLeftValue = colorValue
    }
}