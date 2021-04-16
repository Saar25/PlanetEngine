package org.saar.gui.style.bordercolours

import org.joml.Vector4i
import org.saar.gui.style.Colour
import org.saar.gui.style.StyleProperty

interface ReadonlyBordersColours : StyleProperty {

    var topLeft: Colour
    var topRight: Colour
    var bottomRight: Colour
    var bottomLeft: Colour

    fun asVector4i(vector4i: Vector4i): Vector4i = vector4i.set(
        this.topLeft.asInt(), this.topRight.asInt(),
        this.bottomRight.asInt(), this.bottomLeft.asInt()
    )

}