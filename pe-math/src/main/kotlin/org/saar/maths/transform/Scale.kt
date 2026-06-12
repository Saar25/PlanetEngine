package org.saar.maths.transform

import org.joml.Vector3fc
import org.jproperty.value.ObservableValueBase
import org.saar.maths.utils.Vector3

class Scale private constructor(value: Vector3fc) : ObservableValueBase<Vector3fc>(), ReadonlyScale {

    private val value = Vector3.of(value)

    private fun copyValue(): Vector3fc {
        return Vector3.of(this.value)
    }

    fun scale(scale: Float) {
        val old = copyValue()
        this.value.mul(scale)
        onChange(old)
    }

    fun scale(scale: Vector3fc) {
        val old = copyValue()
        this.value.mul(scale)
        onChange(old)
    }

    fun scale(x: Float, y: Float, z: Float) {
        val old = copyValue()
        this.value.mul(x, y, z)
        onChange(old)
    }

    fun set(scale: ReadonlyScale) {
        val old = copyValue()
        this.value.set(scale.value)
        onChange(old)
    }

    fun set(value: Vector3fc) {
        val old = copyValue()
        this.value.set(value)
        onChange(old)
    }

    fun set(x: Float, y: Float, z: Float) {
        val old = copyValue()
        this.value.set(x, y, z)
        onChange(old)
    }

    fun set(value: Float) {
        val old = copyValue()
        this.value.set(value)
        onChange(old)
    }

    private fun onChange(old: Vector3fc) {
        if (old != this.value) {
            fireChangeEvent(old)
        }
    }

    override fun getValue(): Vector3fc {
        return this.value
    }

    override fun toString(): String {
        return "Scale{" + this.value + '}'
    }

    companion object {
        fun of(x: Float, y: Float, z: Float): Scale {
            return Scale(Vector3.of(x, y, z))
        }

        @JvmStatic
        fun create(): Scale {
            return Scale(Vector3.of(1f))
        }
    }
}
