package org.saar.gui.style.fontcolour

import org.jproperty.constant.ConstantObjectProperty
import org.jproperty.map
import org.saar.gui.style.Colour

object FontColourValues {

    @JvmStatic
    val inherit: FontColourValue = FontColourValue { container ->
        container.parentProperty.map { it.style.fontColour.colour.value }
    }

    @JvmStatic
    fun of(value: Colour): FontColourValue = FontColourValue { ConstantObjectProperty(value) }

}