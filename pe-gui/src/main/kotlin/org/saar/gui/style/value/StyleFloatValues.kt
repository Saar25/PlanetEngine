package org.saar.gui.style.value

object StyleFloatValues {

    @JvmStatic
    val inherit: StyleFloatValue = StyleFloatValue { it }

    @JvmStatic
    val zero: StyleFloatValue = StyleFloatValue { 0f }

    @JvmStatic
    val one: StyleFloatValue = StyleFloatValue { 1f }

    @JvmStatic
    fun of(value: Float): StyleFloatValue = StyleFloatValue { value }

}