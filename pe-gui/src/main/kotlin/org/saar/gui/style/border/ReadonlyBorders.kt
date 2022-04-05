package org.saar.gui.style.border

import org.joml.Vector4i
import org.saar.gui.style.StyleProperty

interface ReadonlyBorders : StyleProperty {

    val top: Int
    val right: Int
    val bottom: Int
    val left: Int

    fun asVector4i(vector4i: Vector4i): Vector4i = vector4i.set(
        this.left, this.top, this.right, this.bottom)
}