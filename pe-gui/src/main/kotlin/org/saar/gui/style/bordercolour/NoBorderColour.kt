package org.saar.gui.style.bordercolour

import org.saar.gui.style.Colour
import org.saar.gui.style.Colours

object NoBorderColour : ReadonlyBorderColour {

    override val colour: Colour = Colours.BLACK

    override fun toString() = "[Corners: ${this.colour}]"
}