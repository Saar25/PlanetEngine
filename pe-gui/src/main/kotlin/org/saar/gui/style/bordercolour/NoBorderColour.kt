package org.saar.gui.style.bordercolour

import org.jproperty.constant.ConstantObjectProperty
import org.saar.gui.style.Colours

object NoBorderColour : ReadonlyBorderColour {

    override val colour = ConstantObjectProperty(Colours.BLACK)

    override fun toString() = "[BorderColour: ${this.colour}]"
}