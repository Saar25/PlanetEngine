package org.saar.maths.transform

import org.joml.Vector3fc
import org.jproperty.value.ObservableValueBase
import org.saar.maths.utils.Vector3

class Position private constructor(value: Vector3fc) : ObservableValueBase<Vector3fc>(), ReadonlyPosition {

    private val value = Vector3.of(value)

    private fun copyValue(): Vector3fc {
        return Vector3.of(this.value)
    }

    fun add(direction: Vector3fc) {
        val old = copyValue()
        this.value.add(direction)
        onChange(old)
    }

    fun add(x: Float, y: Float, z: Float) {
        val old = copyValue()
        this.value.add(x, y, z)
        onChange(old)
    }

    fun sub(direction: Vector3fc) {
        val old = copyValue()
        this.value.sub(direction)
        onChange(old)
    }

    fun sub(x: Float, y: Float, z: Float) {
        val old = copyValue()
        this.value.sub(x, y, z)
        onChange(old)
    }

    fun set(position: ReadonlyPosition) {
        val old = copyValue()
        this.value.set(position.value)
        onChange(old)
    }

    fun set(position: Vector3fc) {
        val old = copyValue()
        this.value.set(position)
        onChange(old)
    }

    fun set(x: Float, y: Float, z: Float) {
        val old = copyValue()
        this.value.set(x, y, z)
        onChange(old)
    }

    private fun onChange(old: Vector3fc) {
        if (old != this.value) {
            fireChangeEvent(old)
        }
    }

    var x: Float
        get() = this.value.x
        set(x) {
            set(x, this.y, this.z)
        }

    fun addX(x: Float) {
        this.x += x
    }

    fun subX(x: Float) {
        this.x -= x
    }

    var y: Float
        get() = this.value.y
        set(y) {
            set(this.x, y, this.z)
        }

    fun addY(y: Float) {
        this.y += y
    }

    fun subY(y: Float) {
        this.y -= y
    }

    var z: Float
        get() = this.value.z
        set(z) {
            set(this.x, this.y, z)
        }

    fun addZ(z: Float) {
        this.z += z
    }

    fun subZ(z: Float) {
        this.z -= z
    }

    override fun getValue(): Vector3fc {
        return this.value
    }

    override fun toString(): String {
        return "Position{" + this.value + '}'
    }

    companion object {
        @JvmStatic
        fun of(x: Float, y: Float, z: Float): Position {
            return Position(Vector3.of(x, y, z))
        }

        @JvmStatic
        fun of(position: Vector3fc): Position {
            return Position(Vector3.of(position))
        }

        @JvmStatic
        fun create(): Position {
            return Position(Vector3.create())
        }
    }
}
