package org.saar.gui.style.fontcolour

import org.saar.gui.style.Colours

object NoFontColour : ReadonlyFontColour {

    override val colour = Colours.BLACK

    override fun toString() = "[FontColour: $colour]"
}