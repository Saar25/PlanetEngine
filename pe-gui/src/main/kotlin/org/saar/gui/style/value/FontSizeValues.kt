package org.saar.gui.style.value

object FontSizeValues {

    @JvmStatic
    val inherit: FontSizeValue = FontSizeValue { parent, _ -> parent.style.fontSize.get() }

    @JvmStatic
    val none: FontSizeValue = FontSizeValue { _, _ -> 0 }

    @JvmStatic
    fun pixels(value: Int): FontSizeValue = FontSizeValue { _, _ -> value }

    @JvmStatic
    fun percent(value: Float): FontSizeValue =
        FontSizeValue { parent, _ -> (parent.style.height.get() * value).toInt() }

    @JvmStatic
    fun percentWidth(value: Float): FontSizeValue =
        FontSizeValue { parent, _ -> (parent.style.width.get() * value).toInt() }

}