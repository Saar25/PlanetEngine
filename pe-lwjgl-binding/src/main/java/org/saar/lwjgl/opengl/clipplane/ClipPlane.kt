package org.saar.lwjgl.opengl.clipplane

import org.joml.Vector4fc
import org.saar.maths.utils.Vector4

class ClipPlane private constructor(val value: Vector4fc) {

    companion object {
        @JvmStatic
        val NONE = of(0f, 0f, 0f, 0f)

        @JvmStatic
        fun of(value: Vector4fc) = ClipPlane(value.normalize(Vector4.create()))

        @JvmStatic
        fun of(a: Float, b: Float, c: Float, d: Float) = ClipPlane(Vector4.of(a, b, c, d).normalize())

        @JvmStatic
        fun ofAbove(height: Float) = of(0f, +1f, 0f, -height)

        @JvmStatic
        fun ofBelow(height: Float) = of(0f, -1f, 0f, +height)
    }


    override fun equals(other: Any?) = other is ClipPlane && other.value == this.value

    override fun hashCode() = this.value.hashCode()

    override fun toString() = "ClipPlane[${this.value.toString()}]"
}