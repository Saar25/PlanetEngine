package org.saar.gui.style.fontcolor

import org.saar.gui.style.Colors

object NoFontColor : ReadonlyFontColor {

    override val color = Colors.BLACK

    override fun toString() = "[FontColor: $color]"
}