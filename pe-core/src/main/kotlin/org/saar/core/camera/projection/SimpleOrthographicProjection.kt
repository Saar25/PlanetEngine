package org.saar.core.camera.projection

import org.joml.Matrix4f
import org.saar.core.camera.Projection
import org.saar.maths.utils.Matrix4
import org.saar.maths.utils.Matrix4.ofProjection

class SimpleOrthographicProjection(
    override val left: Float,
    override val right: Float,
    override val bottom: Float,
    override val top: Float,
    override val zNear: Float,
    override val zFar: Float
) : OrthographicProjection, Projection {

    override val matrix: Matrix4f = Matrix4.create()
        get() = field.ofProjection(this.left, this.right, this.bottom, this.top, this.zNear, this.zFar)
}