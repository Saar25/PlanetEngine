package org.saar.gui.style.fontcolour

import org.saar.gui.style.Colour

object FontColourValues {

    @JvmField
    val inherit: FontColourValue = FontColourValue { container ->
        container.parent.style.fontColour.colour
    }

    @JvmStatic
    fun of(value: Colour): FontColourValue = FontColourValue { value }

}