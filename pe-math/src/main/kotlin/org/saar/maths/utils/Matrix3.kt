package org.saar.maths.utils

import org.joml.Matrix3f
import org.joml.Vector2fc

object Matrix3 {

    private val temp: Matrix3f = create()

    /**
     * Creates a new Matrix3f
     * 
     * @return new Matrix3f
     */
    fun create() = Matrix3f()

    /**
     * Returns a transformation matrix based on the given values
     * 
     * @param position the position
     * @param rotation the rotation
     * @param scaling  the scaling
     * @param dest     the value destination
     * @return the transformation matrix
     */
    @JvmOverloads
    fun ofTransformation(position: Vector2fc, scaling: Vector2fc, rotation: Float, dest: Matrix3f = temp): Matrix3f {
        val sin = Maths.sinf(rotation)
        val cos = Maths.cosf(rotation)
        dest.identity()
            // Translate
            .m02(position.x()).m12(position.y())
            // Rotate
            .m00(cos).m01(-sin)
            .m10(sin).m11(cos)
            // Scale
            .scale(scaling.x(), scaling.y(), 0f)
        return dest
    }

    fun createTransformation(position: Vector2fc, scaling: Vector2fc, rotation: Float): Matrix3f {
        return ofTransformation(position, scaling, rotation, create())
    }

    /**
     * Returns an identity matrix
     * 
     * @param dest the value destination
     * @return the identity matrix
     */
    fun ofIdentity(dest: Matrix3f): Matrix3f {
        dest.identity()
        return dest
    }

    fun ofIdentity(): Matrix3f {
        return temp.identity()
    }
}
