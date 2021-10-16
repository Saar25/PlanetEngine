package org.saar.gui.style.colourmodifier

import org.joml.Vector4fc
import org.saar.gui.style.StyleProperty

interface ReadonlyColourModifier : StyleProperty {
    val multiply: Vector4fc
}