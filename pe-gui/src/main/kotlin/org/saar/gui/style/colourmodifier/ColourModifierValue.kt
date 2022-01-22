package org.saar.gui.style.colourmodifier

import org.joml.Vector4fc
import org.saar.gui.UIChildNode

fun interface ColourModifierValue {

    fun compute(container: UIChildNode): Vector4fc

}