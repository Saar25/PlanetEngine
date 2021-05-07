package org.saar.maths

import org.joml.*

object JomlOperators {

    /* Vector2fc extensions */
    operator fun Vector2fc.plus(other: Vector2fc) {
        add(other, Vector2f())
    }

    operator fun Vector2fc.minus(other: Vector2fc) {
        sub(other, Vector2f())
    }

    operator fun Vector2fc.times(other: Vector2fc) {
        mul(other, Vector2f())
    }

    operator fun Vector2fc.div(other: Vector2fc) {
        mul(1f / other.x(), 1f / other.y(), Vector2f())
    }

    /* Vector2f extensions */
    operator fun Vector2f.plusAssign(other: Vector2fc) {
        add(other)
    }

    operator fun Vector2f.minusAssign(other: Vector2fc) {
        sub(other)
    }

    operator fun Vector2f.timesAssign(other: Vector2fc) {
        mul(other)
    }

    operator fun Vector2f.divAssign(other: Vector2fc) {
        mul(1f / other.x(), 1f / other.y())
    }

    /* Vector3fc extensions */
    operator fun Vector3fc.plus(other: Vector3fc) {
        add(other, Vector3f())
    }

    operator fun Vector3fc.minus(other: Vector3fc) {
        sub(other, Vector3f())
    }

    operator fun Vector3fc.times(other: Vector3fc) {
        mul(other, Vector3f())
    }

    operator fun Vector3fc.div(other: Vector3fc) {
        mul(1f / other.x(), 1f / other.y(), 1f / other.z(), Vector3f())
    }

    /* Vector3f extensions */
    operator fun Vector3f.plusAssign(other: Vector3fc) {
        add(other)
    }

    operator fun Vector3f.minusAssign(other: Vector3fc) {
        sub(other)
    }

    operator fun Vector3f.timesAssign(other: Vector3fc) {
        mul(other)
    }

    operator fun Vector3f.divAssign(other: Vector3fc) {
        mul(1f / other.x(), 1f / other.y(), 1f / other.z())
    }

    /* Vector4fc extensions */
    operator fun Vector4fc.plus(other: Vector4fc) {
        add(other, Vector4f())
    }

    operator fun Vector4fc.minus(other: Vector4fc) {
        sub(other, Vector4f())
    }

    operator fun Vector4fc.times(other: Vector4fc) {
        mul(other, Vector4f())
    }

    operator fun Vector4fc.div(other: Vector4fc) {
        mul(1f / other.x(), 1f / other.y(), 1f / other.z(), 1f / other.w(), Vector4f())
    }

    /* Vector4f extensions */
    operator fun Vector4f.plusAssign(other: Vector4fc) {
        add(other)
    }

    operator fun Vector4f.minusAssign(other: Vector4fc) {
        sub(other)
    }

    operator fun Vector4f.timesAssign(other: Vector4fc) {
        mul(other)
    }

    operator fun Vector4f.divAssign(other: Vector4fc) {
        mul(1f / other.x(), 1f / other.y(), 1f / other.z(), 1f / other.w())
    }

    /* Matrix4f extensions */
    operator fun Matrix4fc.timesAssign(other: Matrix4fc) {
        mul(other, Matrix4f())
    }

    /* Matrix4f extensions */
    operator fun Matrix4f.timesAssign(other: Matrix4fc) {
        mul(other)
    }
}