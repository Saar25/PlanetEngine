package org.saar.gui.style.colourmodifier

import org.joml.Vector4fc
import org.saar.maths.utils.Vector4

object NoColourModifier : ReadonlyColourModifier {

    override val multiply: Vector4fc = Vector4.of(1f)

    override fun toString() = "[Corners: ${this.multiply}]"
}