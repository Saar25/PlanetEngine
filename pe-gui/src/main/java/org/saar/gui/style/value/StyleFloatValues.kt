package org.saar.gui.style.value

object StyleFloatValues {

    @JvmStatic
    val inherit: StyleFloatValue = StyleFloatValue { it }

    @JvmStatic
    fun pixels(value: Float): StyleFloatValue = StyleFloatValue { value }

}