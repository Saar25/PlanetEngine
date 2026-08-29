package org.saar.maths.utils

import org.joml.Vector2fc
import org.joml.Vector3f
import org.joml.Vector3fc
import org.saar.maths.utils.Vector3.create
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.math.tan

object Maths {

    /**
     * Calculates the height of the xz position on the 3 points of the 3D triangle
     * 
     * @param p1 first point of the triangle
     * @param p2 second point of the triangle
     * @param p3 third point of the triangle
     * @param ps x and z position on the triangle
     * @return the y value of the position
     */
    @JvmStatic
    fun barycentric(p1: Vector3fc, p2: Vector3fc, p3: Vector3fc, ps: Vector2fc): Float {
        val det = (p2.z() - p3.z()) * (p1.x() - p3.x()) + (p3.x() - p2.x()) * (p1.z() - p3.z())
        val l1 = ((p2.z() - p3.z()) * (ps.x() - p3.x()) + (p3.x() - p2.x()) * (ps.y() - p3.z())) / det
        val l2 = ((p3.z() - p1.z()) * (ps.x() - p3.x()) + (p1.x() - p3.x()) * (ps.y() - p3.z())) / det
        val l3 = 1.0f - l1 - l2
        return l1 * p1.y() + l2 * p2.y() + l3 * p3.y()
    }

    /**
     * Calculates the normal of a 3D plane given 3 points on the plane that are not on the same line
     * The 3 points must be on clockwise order
     * 
     * @param p1 1st point on the plane
     * @param p2 2nd point on the plane
     * @param p3 3rd point on the plane
     * @return the normal to the plane
     */
    @JvmStatic
    fun calculateNormal(p1: Vector3fc, p2: Vector3fc, p3: Vector3fc): Vector3f {
        val v1 = p2.sub(p1, create())
        val v2 = p3.sub(p1, create())
        return v1.cross(v2).normalize()
    }

    /**
     * Clamp a value between 2 other values
     * 
     * @param a   the value to clamp
     * @param min the minimum value
     * @param max the maximum value
     * @return the clamped value
     */
    fun clamp(a: Float, min: Float, max: Float): Float {
        if (a < min) return min
        return min(a, max)
    }

    /**
     * Clamp a value between 2 other values
     * 
     * @param a   the value to clamp
     * @param min the minimum value
     * @param max the maximum value
     * @return the clamped value
     */
    @JvmStatic
    fun clamp(a: Int, min: Int, max: Int): Int {
        if (a < min) return min
        if (a > max) return max
        return a
    }

    /**
     * Returns whether the given value is higher than min and lower than max
     * 
     * @param a   the value to check
     * @param min the minimum value
     * @param max the maximum value
     * @return true if the value is between false otherwise
     */
    @JvmStatic
    fun isBetween(a: Float, min: Float, max: Float): Boolean {
        return a > min && a < max
    }

    /**
     * Returns whether the given value is higher than min and lower than max or equals to them
     * 
     * @param a   the value to check
     * @param min the minimum value
     * @param max the maximum value
     * @return true if the value is between or equals false otherwise
     */
    @JvmStatic
    fun isInside(a: Float, min: Float, max: Float): Boolean {
        return a in min..max
    }

    /**
     * Mix two vectors using the scalar, for scalar = 0 v1 value will be returned, for scalar = 1 v2 will be returned.
     * Any other scalar value will interpolate between the two vectors
     * 
     * @param vec1   the first vector
     * @param vec2   the second vector
     * @param scalar the scalar
     * @return The mixed vector
     */
    @JvmStatic
    fun mix(vec1: Vector3fc, vec2: Vector3fc, scalar: Float): Vector3f {
        val v = vec1.mul(1 - scalar, create())
        val vector3f = vec2.mul(scalar, create())
        v.add(vector3f)
        return v
    }

    /**
     * Mix two floats using the scalar
     *
     * @param a      the first float
     * @param b      the second float
     * @param scalar the scalar
     * @return The mixed float
     */
    fun mix(a: Float, b: Float, scalar: Float): Float {
        return a * (1 - scalar) + b * scalar
    }

    /**
     * Returns the trigonometric sine of an angle
     * 
     * @param angle the angle in radians
     * @return trigonometric sine of an angle
     */
    fun sinf(angle: Float): Float {
        return sin(angle.toDouble()).toFloat()
    }

    fun sinf(angle: Double): Float {
        return sin(angle).toFloat()
    }

    /**
     * Returns the trigonometric cosine of an angle
     * 
     * @param angle the angle in radians
     * @return trigonometric cosine of an angle
     */
    fun cosf(angle: Float): Float {
        return cos(angle.toDouble()).toFloat()
    }

    fun cosf(angle: Double): Float {
        return cos(angle).toFloat()
    }

    /**
     * Returns the trigonometric tangent of an angle
     * 
     * @param angle the angle in radians
     * @return trigonometric tangent of an angle
     */
    fun tanf(angle: Float): Float {
        return tan(angle.toDouble()).toFloat()
    }

    fun tanf(angle: Double): Float {
        return tan(angle).toFloat()
    }

    /**
     * Returns the sqrt of the value
     * 
     * @param a the the value
     * @return the sqrt of the value
     */
    fun sqrt(a: Float): Float {
        return kotlin.math.sqrt(a.toDouble()).toFloat()
    }
}
