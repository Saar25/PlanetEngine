package org.saar.maths.utils

import org.joml.Matrix4f
import org.joml.Matrix4fc
import org.joml.Quaternionfc
import org.joml.Vector3fc

object Matrix4 {

    val temp: Matrix4f = create()

    /**
     * Creates a new Matrix4f
     * 
     * @return new Matrix4f
     */
    @JvmStatic
    fun create() = Matrix4f()

    /**
     * Creates a new Matrix4f with the given matrix values
     * 
     * @param matrix the matrix to copy
     * @return new Matrix4f
     */
    @JvmStatic
    fun of(matrix: Matrix4fc) = Matrix4f(matrix)

    /**
     * Returns a perspective projection matrix
     * 
     * @param fov    the field of view in radians
     * @param width  the width of the screen
     * @param height the height of the screen
     * @param zNear  the near plane
     * @param zFar   the far plane
     * @param dest   the value destination
     * @return the projection matrix
     */
    @JvmStatic
    fun ofProjection(fov: Float, width: Float, height: Float, zNear: Float, zFar: Float, dest: Matrix4f): Matrix4f {
        return dest.setPerspective(fov, width / height, zNear, zFar)
    }

    fun createProjection(fov: Float, width: Float, height: Float, zNear: Float, zFar: Float): Matrix4f {
        return ofProjection(fov, width, height, zNear, zFar, create())
    }

    /**
     * Returns an orthographic projection matrix
     * 
     * @param left   the left frustum edge
     * @param right  the right frustum edge
     * @param bottom the bottom frustum edge
     * @param top    the top frustum edge
     * @param zNear  the near clipping plane distance
     * @param zFar   the far clipping plane distance
     * @param dest   the value destination
     * @return the projection matrix
     */
    @JvmStatic
    fun ofProjection(left: Float, right: Float, bottom: Float, top: Float,
                     zNear: Float, zFar: Float, dest: Matrix4f): Matrix4f {
        return dest.setOrtho(left, right, bottom, top, zNear, zFar)
    }

    fun createProjection(left: Float, right: Float, bottom: Float, top: Float, zNear: Float, zFar: Float): Matrix4f {
        return ofProjection(left, right, bottom, top, zNear, zFar, create())
    }

    /**
     * Returns a view matrix based on the camera
     * 
     * @param position the position camera
     * @param rotation the rotation camera
     * @param dest     the value destination
     * @return the view matrix
     */
    fun ofView(position: Vector3fc, rotation: Vector3fc, dest: Matrix4f): Matrix4f {
        return dest.identity().rotateXYZ(rotation)
            .translate(-position.x(), -position.y(), -position.z())
    }

    fun createView(position: Vector3fc, rotation: Vector3fc): Matrix4f {
        return ofView(position, rotation, create())
    }

    /**
     * Returns a view matrix based on the camera
     * 
     * @param position the position camera
     * @param rotation the rotation camera
     * @param dest     the value destination
     * @return the view matrix
     */
    @JvmStatic
    fun ofView(position: Vector3fc, rotation: Quaternionfc, dest: Matrix4f): Matrix4f {
        return dest.identity().translationRotateScaleInvert(position, rotation, 1f)
    }

    fun createView(position: Vector3fc, rotation: Quaternionfc): Matrix4f {
        return ofView(position, rotation, create())
    }

    /**
     * Returns a transformation matrix based on the given values
     * 
     * @param position the position
     * @param rotation the rotation
     * @param scale    the scale
     * @param dest     the value destination
     * @return the transformation matrix
     */
    @JvmStatic
    fun ofTransformation(position: Vector3fc, rotation: Quaternionfc, scale: Vector3fc, dest: Matrix4f): Matrix4f {
        return dest.identity().translationRotateScale(position, rotation, scale)
    }
}
