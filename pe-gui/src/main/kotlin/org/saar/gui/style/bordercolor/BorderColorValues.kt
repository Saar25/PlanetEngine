package org.saar.gui.style.bordercolor

import org.saar.gui.style.Color

object BorderColorValues {

    @JvmField
    val inherit: BorderColorValue = BorderColorValue { it.parent.style.borderColor.color }

    @JvmStatic
    fun of(value: Color): BorderColorValue = BorderColorValue { value }

}