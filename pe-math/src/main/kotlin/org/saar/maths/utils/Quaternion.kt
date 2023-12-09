package org.saar.maths.utils

import org.joml.Quaternionf
import org.joml.Quaternionfc
import org.joml.Vector3fc

object Quaternion {

    /**
     * Creates a new Quaternionf
     * 
     * @return a new Quaternionf
     */
    @JvmStatic
    fun create() = Quaternionf()

    /**
     * Creates a new Quaternionf and initialize it with the given values
     * 
     * @param x the x component
     * @param y the y component
     * @param z the z component
     * @param w the w component
     * @return a new Quaternionf
     */
    @JvmStatic
    fun of(x: Float, y: Float, z: Float, w: Float) = Quaternionf(x, y, z, w)

    /**
     * Creates a new Quaternion with the same values as q
     * 
     * @param q the quaternion to copy the values from
     * @return a new Quaternionf
     */
    @JvmStatic
    fun of(q: Quaternionfc) = of(q.x(), q.y(), q.z(), q.w())

    /**
     * Creates a new Quaternionf that rotated toward the direction given
     * 
     * @param direction the direction
     * @return a new Quaternion
     */
    fun ofDirection(direction: Vector3fc, dest: Quaternionf): Quaternionf {
        dest.identity()
        dest.lookAlong(direction, Vector3.UP)
        return dest
    }

    fun createDirection(direction: Vector3fc) = ofDirection(direction, create())

    /*
     *
     * Methods for static operations on vectors
     *
     */
    fun add(a: Quaternionf, b: Quaternionf): Quaternionf = of(a).add(b)

    fun mul(a: Quaternionf, b: Quaternionf): Quaternionf = of(a).mul(b)
}
