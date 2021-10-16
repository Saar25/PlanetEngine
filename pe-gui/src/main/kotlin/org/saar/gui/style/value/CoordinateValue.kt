package org.saar.gui.style.value

import org.saar.gui.style.IStyle

interface CoordinateValue {
    fun computeAxisX(parent: IStyle, style: IStyle): Int

    fun computeAxisY(parent: IStyle, style: IStyle): Int
}