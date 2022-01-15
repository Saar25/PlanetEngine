package org.saar.gui.style.margin

import org.joml.Vector4i
import org.jproperty.value.ObservableIntegerValue
import org.saar.gui.style.StyleProperty

interface ReadonlyMargin : StyleProperty {

    val top: ObservableIntegerValue
    val right: ObservableIntegerValue
    val bottom: ObservableIntegerValue
    val left: ObservableIntegerValue

    fun asVector4i(vector4i: Vector4i): Vector4i = vector4i.set(
        this.top.get(), this.right.get(), this.bottom.get(), this.left.get())
}