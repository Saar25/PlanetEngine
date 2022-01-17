package org.saar.gui.style.fontsize

import org.jproperty.constant.ConstantInteger
import org.jproperty.mapToInteger

object FontSizeValues {

    @JvmStatic
    val inherit: FontSizeValue = FontSizeValue { container ->
        container.parentProperty.mapToInteger { it.style.fontSize.size.get() }
    }

    @JvmStatic
    val none: FontSizeValue = FontSizeValue { ConstantInteger(0) }

    @JvmStatic
    fun pixels(value: Int): FontSizeValue = FontSizeValue { ConstantInteger(value) }

    @JvmStatic
    fun percent(value: Float): FontSizeValue = FontSizeValue { container ->
        container.parentProperty.mapToInteger { (it.style.height.get() * value).toInt() }
    }

    @JvmStatic
    fun percentWidth(value: Float): FontSizeValue = FontSizeValue { container ->
        container.parentProperty.mapToInteger { (it.style.width.get() * value).toInt() }
    }

}