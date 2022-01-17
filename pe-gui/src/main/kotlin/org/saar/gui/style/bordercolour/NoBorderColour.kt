package org.saar.gui.style.bordercolour

import org.jproperty.constant.ConstantObject
import org.saar.gui.style.Colours

object NoBorderColour : ReadonlyBorderColour {

    override val colour = ConstantObject(Colours.BLACK)

    override fun toString() = "[BorderColour: ${this.colour}]"
}