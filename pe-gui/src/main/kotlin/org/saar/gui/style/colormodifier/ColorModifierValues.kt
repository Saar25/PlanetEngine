package org.saar.gui.style.colormodifier

import org.joml.Vector4fc

object ColorModifierValues {

    @JvmField
    val inherit: ColorModifierValue = ColorModifierValue { it.parent.style.colorModifier.multiply }

    @JvmStatic
    fun of(value: Vector4fc): ColorModifierValue = ColorModifierValue { value }

}