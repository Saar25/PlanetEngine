package org.saar.gui.style.colourmodifier

import org.joml.Vector4fc

object ColourModifierValues {

    @JvmField
    val inherit: ColourModifierValue = ColourModifierValue { it.parent.style.colourModifier.multiply }

    @JvmStatic
    fun of(value: Vector4fc): ColourModifierValue = ColourModifierValue { value }

}