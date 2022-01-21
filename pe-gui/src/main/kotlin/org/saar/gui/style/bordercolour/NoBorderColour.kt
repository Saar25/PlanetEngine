package org.saar.gui.style.bordercolour

import org.saar.gui.style.Colours

object NoBorderColour : ReadonlyBorderColour {

    override val colour = Colours.BLACK

    override fun toString() = "[BorderColour: $colour]"
}