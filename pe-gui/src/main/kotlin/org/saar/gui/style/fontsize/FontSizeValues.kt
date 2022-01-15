package org.saar.gui.style.fontsize

object FontSizeValues {

    @JvmStatic
    val inherit: FontSizeValue = FontSizeValue { it.parent.style.fontSize.get() }

    @JvmStatic
    val none: FontSizeValue = FontSizeValue { 0 }

    @JvmStatic
    fun pixels(value: Int): FontSizeValue = FontSizeValue { value }

    @JvmStatic
    fun percent(value: Float): FontSizeValue = FontSizeValue { (it.parent.style.height.get() * value).toInt() }

    @JvmStatic
    fun percentWidth(value: Float): FontSizeValue = FontSizeValue { (it.parent.style.width.get() * value).toInt() }

}