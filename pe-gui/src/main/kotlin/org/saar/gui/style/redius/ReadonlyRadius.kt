package org.saar.gui.style.redius

import org.joml.Vector4i
import org.saar.gui.style.StyleProperty

interface ReadonlyRadius : StyleProperty {

    val topRight: Int
    val topLeft: Int
    val bottomRight: Int
    val bottomLeft: Int

    fun get(right: Boolean, top: Boolean): Int {
        return when {
            top && right -> this.topRight
            top && !right -> this.topLeft
            !top && right -> this.bottomRight
            else -> this.bottomLeft
        }
    }

    fun isZero(): Boolean {
        return this.topRight == 0 && this.topLeft == 0
                && this.bottomRight == 0 && this.bottomLeft == 0
    }

    fun asVector4i(vector4i: Vector4i): Vector4i = vector4i.set(
        this.topRight, this.topLeft, this.bottomRight, this.bottomLeft)
}