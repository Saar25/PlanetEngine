package org.saar.gui.style.value

import org.saar.gui.style.Colour

object StyleColourValues {

    @JvmStatic
    val inherit: StyleColourValue = StyleColourValue { it }

    @JvmStatic
    fun of(value: Colour): StyleColourValue = StyleColourValue { value }

}