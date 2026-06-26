package org.saar.gui.style.colormodifier

import org.joml.Vector4fc
import org.saar.gui.style.StyleProperty

interface ReadonlyColorModifier : StyleProperty {
    val multiply: Vector4fc
}