package org.saar.gui.style.value

import org.saar.gui.style.IStyle
import org.saar.gui.style.ITextStyle

object TextLengthValues {

    @JvmStatic
    val zero = TextLengthValue.Simple { _, _ -> 0 }

    @JvmStatic
    val inherit = object : TextLengthValue {
        override fun computeAxisX(parent: IStyle, style: ITextStyle) = parent.width.get()
        override fun computeAxisY(parent: IStyle, style: ITextStyle) = parent.height.get()
    }

    @JvmStatic
    fun pixels(pixels: Int) = TextLengthValue.Simple { _, _ -> pixels }

    @JvmStatic
    fun percent(percents: Float) = object : TextLengthValue {
        override fun computeAxisX(parent: IStyle, style: ITextStyle) =
            (parent.width.get() * percents / 100).toInt()

        override fun computeAxisY(parent: IStyle, style: ITextStyle) =
            (parent.height.get() * percents / 100).toInt()
    }

    @JvmStatic
    fun fitContent() = object : TextLengthValue {
        override fun computeAxisX(parent: IStyle, style: ITextStyle) = style.contentWidth.get()
        override fun computeAxisY(parent: IStyle, style: ITextStyle) = style.contentHeight.get()
    }
}