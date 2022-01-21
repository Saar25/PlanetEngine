package org.saar.gui.style.backgroundcolour

import org.joml.Vector4i
import org.saar.gui.style.Colour

interface ReadonlyBackgroundColour {

    val topRight: Colour
    val topLeft: Colour
    val bottomRight: Colour
    val bottomLeft: Colour

    fun asVector4i(vector4i: Vector4i): Vector4i = vector4i.set(
        this.topRight.asInt(), this.topLeft.asInt(),
        this.bottomRight.asInt(), this.bottomLeft.asInt())

}