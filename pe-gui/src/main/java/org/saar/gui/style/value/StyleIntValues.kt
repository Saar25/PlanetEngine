package org.saar.gui.style.value

object StyleIntValues {

    @JvmStatic
    val inherit: StyleIntValue = StyleIntValue { it }

    @JvmStatic
    fun pixels(value: Int): StyleIntValue = StyleIntValue { value }

}