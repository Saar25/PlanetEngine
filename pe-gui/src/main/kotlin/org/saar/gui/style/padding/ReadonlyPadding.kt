package org.saar.gui.style.padding

import org.joml.Vector4i
import org.saar.gui.style.StyleProperty

interface ReadonlyPadding : StyleProperty {

    val top: Int
    val right: Int
    val bottom: Int
    val left: Int

    fun asVector4i(vector4i: Vector4i): Vector4i = vector4i.set(
        this.top, this.right, this.bottom, this.left)
}