package org.saar.maths.utils

import org.joml.Vector2fc
import org.joml.Vector3fc
import org.joml.Vector4f
import org.joml.Vector4fc

object Vector4 {

    val temp: Vector4f = Vector4f()

    /**
     * Creates a new zero vector
     * 
     * @return a new zero Vector4f
     */
    fun create(): Vector4f {
        return Vector4f()
    }

    /**
     * Creates a new Vector4f with the given values
     * 
     * @param x the x component
     * @param y the y component
     * @param z the z component
     * @param w the w component
     * @return a new Vector4f
     */
    @JvmStatic
    fun of(x: Float, y: Float, z: Float, w: Float) = Vector4f(x, y, z, w)

    /**
     * Creates a new Vector4f and initialize it with the given value
     * 
     * @param d the value of all four components
     * @return a new Vector4f
     */
    fun of(d: Float) = of(d, d, d, d)

    /**
     * Creates a new Vector4f with the same values as v
     * 
     * @param v the Vector4fc to copy the values from
     * @return a new Vector4f
     */
    @JvmStatic
    fun of(v: Vector4fc) = of(v.x(), v.y(), v.z(), v.w())

    /**
     * Creates a new Vector4f with the same values as v and w
     * 
     * @param v the Vector3fc to copy the xyz values from
     * @param w the w value of the new Vector4f
     * @return a new Vector4f
     */
    fun of(v: Vector3fc, w: Float) = of(v.x(), v.y(), v.z(), w)

    /**
     * Creates a new Vector4f with the same values as v and z and w
     * 
     * @param v the Vector2fc to copy the xy values from
     * @param z the z value of the new Vector4f
     * @param w the w value of the new Vector4f
     * @return a new Vector4f
     */
    fun of(v: Vector2fc, z: Float, w: Float) = of(v.x(), v.y(), z, w)

    /**
     * Creates a new Vector4f with the same values as v1 and v2
     * 
     * @param v1 the Vector2fc to copy the xy values from
     * @param v2 the Vector2fc to copy the zw values from
     * @return a new Vector4f
     */
    fun of(v1: Vector2fc, v2: Vector2fc) = of(v1.x(), v1.y(), v2.x(), v2.y())
}
