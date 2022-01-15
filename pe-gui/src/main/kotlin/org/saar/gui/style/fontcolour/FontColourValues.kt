package org.saar.gui.style.fontcolour

import org.saar.gui.style.Colour

object FontColourValues {

    @JvmStatic
    val inherit: FontColourValue = FontColourValue { it.parent.style.fontColour.colour }

    @JvmStatic
    fun of(value: Colour): FontColourValue = FontColourValue { value }

}