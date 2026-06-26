package org.saar.gui.style.bordercolor

import org.saar.gui.style.Colors

object NoBorderColor : ReadonlyBorderColor {

    override val color = Colors.BLACK

    override fun toString() = "[BorderColor: $color]"
}