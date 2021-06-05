package org.saar.gui.style.value

object StyleIntValues {

    @JvmStatic
    val inherit: StyleIntValue = StyleIntValue { it }

    @JvmStatic
    val none: StyleIntValue = StyleIntValue { 0 }

    @JvmStatic
    fun pixels(value: Int): StyleIntValue = StyleIntValue { value }

}