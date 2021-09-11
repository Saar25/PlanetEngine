package org.saar.gui.style.fontcolour

import org.saar.gui.style.Colour
import org.saar.gui.style.Colours

object NoFontColour : ReadonlyFontColour {

    override val colour: Colour = Colours.BLACK

    override fun toString() = "[Corners: ${this.colour}]"
}