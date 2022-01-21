package org.saar.gui.style.bordercolour

import org.saar.gui.style.Colour

object BorderColourValues {

    @JvmStatic
    val inherit: BorderColourValue = BorderColourValue { it.parent.style.borderColour.colour }

    @JvmStatic
    fun of(value: Colour): BorderColourValue = BorderColourValue { value }

}