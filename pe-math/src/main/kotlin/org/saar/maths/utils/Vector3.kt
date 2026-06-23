package org.saar.maths.utils

import org.joml.Vector3f
import org.joml.Vector3fc
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
    fun of(d: Float) = Vector3f(d)

    /**
     * Creates a new Vector3f with the same values as v
     * 
     * @param v the Vector3fc to copy the values from
     * @return a new Vector3f
     */
    @JvmStatic
    fun of(v: Vector3fc) = Vector3f(v)

    @JvmStatic
    fun upward() = of(UP)

    @JvmStatic
    fun right() = of(RIGHT)

    @JvmStatic
    fun forward() = of(FORWARD)

    @JvmStatic
    fun downward() = of(DOWN)

    @JvmStatic
    fun left() = of(LEFT)

    @JvmStatic
    fun backward() = of(BACKWARD)

    @JvmStatic
    fun one() = of(ONE)

    @JvmStatic
    fun zero() = of(ZERO)

    @JvmStatic
    fun add(v1: Vector3fc, v2: Vector3fc): Vector3f = v1.add(v2, create())

    @JvmStatic
    fun sub(v1: Vector3fc, v2: Vector3fc): Vector3f = v1.sub(v2, create())

    @JvmStatic
    fun mul(v1: Vector3fc, v2: Vector3fc): Vector3f = v1.mul(v2, create())

    @JvmStatic
    fun div(v1: Vector3fc, v2: Vector3fc): Vector3f = v1.div(v2, create())

    @JvmStatic
    fun mul(v: Vector3fc, scalar: Float): Vector3f = v.mul(scalar, create())

    @JvmStatic
    fun div(v: Vector3fc, scalar: Float): Vector3f = v.div(scalar, create())

    @JvmStatic
    fun cross(v1: Vector3fc, v2: Vector3fc): Vector3f = v1.cross(v2, create())

    @JvmStatic
    fun normalize(x: Float, y: Float, z: Float): Vector3f = of(x, y, z).normalize()

    @JvmStatic
    fun normalize(vector: Vector3fc): Vector3f = vector.normalize(create())

    @JvmStatic
    fun length(x: Float, y: Float, z: Float) = sqrt((x * x + y * y + z * z).toDouble()).toFloat()

    @JvmStatic
    fun randomize(vector: Vector3f = create()): Vector3f = vector.set(Math.random(), Math.random(), Math.random())
}