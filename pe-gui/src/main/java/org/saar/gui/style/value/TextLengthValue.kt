package org.saar.gui.style.value

import org.saar.gui.style.IStyle
import org.saar.gui.style.ITextStyle

interface TextLengthValue {
    fun computeAxisX(parent: IStyle, style: ITextStyle): Int

    fun computeAxisY(parent: IStyle, style: ITextStyle): Int

    fun computeMaxAxisX(parent: IStyle, style: ITextStyle): Int = computeAxisX(parent, style)

    fun computeMaxAxisY(parent: IStyle, style: ITextStyle): Int = computeAxisY(parent, style)

    fun interface Simple : TextLengthValue {
        fun compute(parent: IStyle, style: ITextStyle): Int

        override fun computeAxisX(parent: IStyle, style: ITextStyle) = compute(parent, style)
        override fun computeAxisY(parent: IStyle, style: ITextStyle) = compute(parent, style)
    }
}