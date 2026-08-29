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
    fun ofProjection(fov: Float, width: Float, height: Float, zNear: Float, zFar: Float, dest: Matrix4f): Matrix4f =
        dest.setPerspective(fov, width / height, zNear, zFar)

    fun Matrix4f.ofProjection(fov: Float, width: Float, height: Float, zNear: Float, zFar: Float) =
        ofProjection(fov, width, height, zNear, zFar, this)

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
    fun ofProjection(
        left: Float, right: Float, bottom: Float, top: Float,
        zNear: Float, zFar: Float, dest: Matrix4f
    ): Matrix4f {
        return dest.setOrtho(left, right, bottom, top, zNear, zFar)
    }

    fun Matrix4f.ofProjection(left: Float, right: Float, bottom: Float, top: Float, zNear: Float, zFar: Float) =
        ofProjection(left, right, bottom, top, zNear, zFar, this)

    /**
     * Returns a view matrix based on the camera
     * 
     * @param position the position camera
     * @param rotation the rotation camera
     * @param dest     the value destination
     * @return the view matrix
     */
    @JvmStatic
    fun ofView(position: Vector3fc, rotation: Quaternionfc, dest: Matrix4f): Matrix4f =
        dest.translationRotateScaleInvert(position, rotation, 1f)

    fun Matrix4f.ofView(position: Vector3fc, rotation: Quaternionfc) = ofView(position, rotation, this)

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
    fun ofTransformation(position: Vector3fc, rotation: Quaternionfc, scale: Vector3fc, dest: Matrix4f): Matrix4f =
        dest.translationRotateScale(position, rotation, scale)

    fun Matrix4f.ofTransformation(position: Vector3fc, rotation: Quaternionfc, scale: Vector3fc) =
        ofTransformation(position, rotation, scale, this)
}
