package org.saar.maths.utils

import org.joml.Vector2fc
import org.joml.Vector3f
import org.joml.Vector3fc
import org.joml.Vector4fc
import kotlin.math.sqrt

object Vector3 {

    @JvmField
    val UP: Vector3fc = of(0f, 1f, 0f)

    @JvmField
    val RIGHT: Vector3fc = of(1f, 0f, 0f)

    @JvmField
    val FORWARD: Vector3fc = of(0f, 0f, 1f)

    @JvmField
    val DOWN: Vector3fc = of(0f, -1f, 0f)

    @JvmField
    val LEFT: Vector3fc = of(-1f, 0f, 0f)

    @JvmField
    val BACKWARD: Vector3fc = of(0f, 0f, -1f)

    @JvmField
    val ONE: Vector3fc = of(1f, 1f, 1f)

    @JvmField
    val ZERO: Vector3fc = of(0f, 0f, 0f)

    /**
     * Creates a new zero vector
     * 
     * @return a new zero Vector3f
     */
    @JvmStatic
    fun create() = Vector3f()

    /**
     * Creates a new Vector3f and initialize it with the given values
     * 
     * @param x the x component
     * @param y the y component
     * @param z the z component
     * @return a new Vector3f
     */
    @JvmStatic
    fun of(x: Float, y: Float, z: Float) = Vector3f(x, y, z)

    /**
     * Creates a new Vector3f and initialize it with the given value
     * 
     * @param d the value of all three components
     * @return a new Vector3f
     */
    @JvmStatic
    fun of(d: Float) = of(d, d, d)

    /**
     * Creates a new Vector3f with the xyz values of v
     * 
     * @param v the Vector4fc to copy the xyz values from
     * @return a new Vector3f
     */
    fun of(v: Vector4fc) = of(v.x(), v.y(), v.z())

    /**
     * Creates a new Vector3f with the same values as v
     * 
     * @param v the Vector3fc to copy the values from
     * @return a new Vector3f
     */
    @JvmStatic
    fun of(v: Vector3fc) = of(v.x(), v.y(), v.z())

    /**
     * Creates a new Vector3f with the same values as v and z
     * 
     * @param v the Vector2fc to copy the xy values from
     * @param z the z value of the new Vector3f
     * @return a new Vector3f
     */
    fun of(v: Vector2fc, z: Float) = of(v.x(), v.y(), z)

    @JvmStatic
    fun upward() = of(UP.x(), UP.y(), UP.z())

    @JvmStatic
    fun right() = of(RIGHT.x(), RIGHT.y(), RIGHT.z())

    @JvmStatic
    fun forward() = of(FORWARD.x(), FORWARD.y(), FORWARD.z())

    @JvmStatic
    fun downward() = of(DOWN.x(), DOWN.y(), DOWN.z())

    @JvmStatic
    fun left() = of(LEFT.x(), LEFT.y(), LEFT.z())

    @JvmStatic
    fun backward() = of(BACKWARD.x(), BACKWARD.y(), BACKWARD.z())

    @JvmStatic
    fun one() = of(ONE.x(), ONE.y(), ONE.z())

    @JvmStatic
    fun zero() = of(ZERO.x(), ZERO.y(), ZERO.z())

    @JvmStatic
    fun add(v1: Vector3fc, v2: Vector3fc): Vector3f = of(v1).add(v2)

    @JvmStatic
    fun sub(v1: Vector3fc, v2: Vector3fc): Vector3f = of(v1).sub(v2)

    @JvmStatic
    fun mul(v1: Vector3fc, v2: Vector3fc): Vector3f = of(v1).mul(v2)

    @JvmStatic
    fun div(v1: Vector3fc, v2: Vector3fc): Vector3f = of(v1).div(v2)

    @JvmStatic
    fun mul(v: Vector3fc, scalar: Float): Vector3f = of(v).mul(scalar)

    @JvmStatic
    fun div(v: Vector3fc, scalar: Float): Vector3f = of(v).div(scalar)

    @JvmStatic
    fun cross(v1: Vector3fc, v2: Vector3fc): Vector3f = of(v1).cross(v2)

    @JvmStatic
    fun normalize(x: Float, y: Float, z: Float): Vector3f = of(x, y, z).normalize()

    @JvmStatic
    fun normalize(vector: Vector3fc): Vector3f = of(vector).normalize()

    @JvmStatic
    fun length(x: Float, y: Float, z: Float) = sqrt((x * x + y * y + z * z).toDouble()).toFloat()

    @JvmStatic
    fun randomize(vector: Vector3f): Vector3f = vector.set(Math.random(), Math.random(), Math.random())
}