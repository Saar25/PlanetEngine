package org.saar.gui.style.fontsize

object FontSizeValues {

    @JvmField
    val inherit: FontSizeValue = FontSizeValue { it.parent.style.fontSize.size }

    @JvmField
    val none: FontSizeValue = FontSizeValue { 0 }

    @JvmStatic
    fun pixels(value: Int): FontSizeValue = FontSizeValue { value }

    @JvmStatic
    fun percent(value: Float): FontSizeValue = FontSizeValue { (it.parent.style.height.getMax() * value).toInt() }

    @JvmStatic
    fun percentWidth(value: Float): FontSizeValue = FontSizeValue { (it.parent.style.width.getMax() * value).toInt() }

}