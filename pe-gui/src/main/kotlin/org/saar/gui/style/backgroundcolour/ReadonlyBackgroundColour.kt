package org.saar.gui.style.backgroundcolour

import org.joml.Vector4i
import org.jproperty.ObservableValue
import org.saar.gui.style.Colour

interface ReadonlyBackgroundColour {

    val topRight: ObservableValue<Colour>
    val topLeft: ObservableValue<Colour>
    val bottomRight: ObservableValue<Colour>
    val bottomLeft: ObservableValue<Colour>

    fun asVector4i(vector4i: Vector4i): Vector4i = vector4i.set(
        this.topRight.value.asInt(), this.topLeft.value.asInt(),
        this.bottomRight.value.asInt(), this.bottomLeft.value.asInt())

}