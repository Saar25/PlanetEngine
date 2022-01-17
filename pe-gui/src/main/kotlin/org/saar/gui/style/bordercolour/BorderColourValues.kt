package org.saar.gui.style.bordercolour

import org.jproperty.constant.ConstantObject
import org.jproperty.map
import org.saar.gui.style.Colour

object BorderColourValues {

    @JvmStatic
    val inherit: BorderColourValue = BorderColourValue { container ->
        container.parentProperty.map { it.style.borderColour.colour.value }
    }

    @JvmStatic
    fun of(value: Colour): BorderColourValue = BorderColourValue { ConstantObject(value) }

}