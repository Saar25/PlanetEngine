package org.saar.gui.style.fontcolor

import org.saar.gui.style.Color

object FontColorValues {

    @JvmField
    val inherit: FontColorValue = FontColorValue { container ->
        container.parent.style.fontColor.color
    }

    @JvmStatic
    fun of(value: Color): FontColorValue = FontColorValue { value }

}