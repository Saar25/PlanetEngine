package org.saar.gui.style.opacity

object OpacityValues {

    @JvmField
    val inherit: OpacityValue = OpacityValue { it.parent.style.opacity.opacity }

    @JvmStatic
    fun of(opacity: Float): OpacityValue = OpacityValue { opacity }

}