package org.saar.maths.transform

import org.joml.Quaternionfc
import org.joml.Vector3f
import org.joml.Vector3fc
import org.jproperty.value.ObservableValueBase
import org.saar.maths.Angle
import org.saar.maths.utils.Quaternion
import org.saar.maths.utils.Vector3

class Rotation private constructor(value: Quaternionfc) : ObservableValueBase<Quaternionfc>(), ReadonlyRotation {

    private val value = Quaternion.of(value)

    override val eulerAngles: Vector3f = Vector3.create()
        get() {
            val value = this.value
            return value.getEulerAnglesXYZ(field)
        }

    private fun copyValue(): Quaternionfc {
        return Quaternion.of(this.value)
    }

    fun set(rotation: ReadonlyRotation) {
        val old = copyValue()
        this.value.set(rotation.value)
        onChange(old)
    }

    fun set(rotation: Quaternionfc) {
        val old = copyValue()
        this.value.set(rotation)
        onChange(old)
    }

    fun rotate(x: Angle, y: Angle, z: Angle) {
        rotateRadians(x.radians, y.radians, z.radians)
    }

    fun rotateDegrees(x: Float, y: Float, z: Float) {
        val xRadians = Math.toRadians(x.toDouble()).toFloat()
        val yRadians = Math.toRadians(y.toDouble()).toFloat()
        val zRadians = Math.toRadians(z.toDouble()).toFloat()
        rotateRadians(xRadians, yRadians, zRadians)
    }

    fun rotateRadians(x: Float, y: Float, z: Float) {
        val old = copyValue()
        this.value.rotateX(x)
            .rotateLocalY(y)
            .rotateZ(z)
        onChange(old)
    }

    fun rotate(rotation: Rotation) {
        rotate(rotation.value)
    }

    fun rotate(rotation: Quaternionfc) {
        val old = copyValue()
        this.value.mul(rotation)
        onChange(old)
    }

    private fun onChange(old: Quaternionfc) {
        if (old != this.value) {
            fireChangeEvent(old)
        }
    }

    fun lookAlong(direction: Vector3fc) {
        var direction = direction
        direction = Vector3.normalize(direction)
        if (direction.equals(Vector3.DOWN, 0f)) {
            set(Quaternion.of(-1f, 0f, 0f, 1f).normalize())
        } else if (!direction.equals(Vector3.ZERO, 0f)) {
            set(Quaternion.create().lookAlong(
                direction, Vector3.UP).conjugate())
        }
    }

    override fun getValue(): Quaternionfc {
        return this.value
    }

    override val direction: Vector3f
        get() = Vector3.forward().rotate(this.value)

    override fun toString(): String {
        return "Rotation{" + this.value + '}'
    }

    companion object {
        fun fromEulerAngles(eulerAngles: Vector3fc): Rotation {
            return fromEulerAngles(eulerAngles.x(), eulerAngles.y(), eulerAngles.z())
        }

        fun fromEulerAngles(x: Float, y: Float, z: Float): Rotation {
            return Rotation(Quaternion.create().rotateXYZ(x, y, z))
        }

        fun fromQuaternion(quaternion: Quaternionfc): Rotation {
            return Rotation(Quaternion.of(quaternion))
        }

        fun fromQuaternion(x: Float, y: Float, z: Float, w: Float): Rotation {
            return Rotation(Quaternion.of(x, y, z, w))
        }

        fun create(): Rotation {
            return Rotation(Quaternion.create())
        }
    }
}
