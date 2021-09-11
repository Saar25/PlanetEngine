package org.saar.gui.style.value

import org.saar.gui.style.IStyle

interface LengthValue {
    fun computeAxisX(parent: IStyle, style: IStyle): Int

    fun computeAxisY(parent: IStyle, style: IStyle): Int

    fun interface Simple : LengthValue {
        fun compute(parent: IStyle, style: IStyle): Int

        override fun computeAxisX(parent: IStyle, style: IStyle) = compute(parent, style)
        override fun computeAxisY(parent: IStyle, style: IStyle) = compute(parent, style)
    }
}