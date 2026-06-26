package org.saar.gui.style.colormodifier

import org.joml.Vector4fc
import org.saar.maths.utils.Vector4

object NoColorModifier : ReadonlyColorModifier {

    override val multiply: Vector4fc = Vector4.of(1f)

    override fun toString() = "[Corners: ${this.multiply}]"
}