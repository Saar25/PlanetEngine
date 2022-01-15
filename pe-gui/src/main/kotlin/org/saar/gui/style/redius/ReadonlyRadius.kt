package org.saar.gui.style.redius

import org.joml.Vector4i
import org.jproperty.value.ObservableIntegerValue
import org.saar.gui.style.StyleProperty

interface ReadonlyRadius : StyleProperty {

    val topRight: ObservableIntegerValue
    val topLeft: ObservableIntegerValue
    val bottomRight: ObservableIntegerValue
    val bottomLeft: ObservableIntegerValue

    fun get(right: Boolean, top: Boolean): Int {
        return when {
            top && right -> this.topRight.get()
            top && !right -> this.topLeft.get()
            !top && right -> this.bottomRight.get()
            else -> this.bottomLeft.get()
        }
    }

    fun isZero(): Boolean {
        return this.topRight.get() == 0 && this.topLeft.get() == 0
                && this.bottomRight.get() == 0 && this.bottomLeft.get() == 0
    }

    fun asVector4i(vector4i: Vector4i): Vector4i = vector4i.set(
        this.topRight.get(), this.topLeft.get(), this.bottomRight.get(), this.bottomLeft.get())
}