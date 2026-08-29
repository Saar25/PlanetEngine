package org.saar.gui.style.backgroundcolor

import org.joml.Vector4i
import org.saar.gui.style.Color

interface ReadonlyBackgroundColor {

    val topRight: Color
    val topLeft: Color
    val bottomRight: Color
    val bottomLeft: Color

    fun asVector4i(vector4i: Vector4i): Vector4i = vector4i.set(
        this.topRight.asInt(), this.topLeft.asInt(),
        this.bottomRight.asInt(), this.bottomLeft.asInt())

}