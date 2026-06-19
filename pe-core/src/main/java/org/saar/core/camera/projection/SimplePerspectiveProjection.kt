package org.saar.core.camera.projection

import org.joml.Matrix4f
import org.saar.core.camera.Projection
import org.saar.maths.utils.Matrix4
import org.saar.maths.utils.Matrix4.ofProjection

class SimplePerspectiveProjection(
    override var fov: Float,
    override var width: Float,
    override var height: Float,
    override var near: Float,
    override var far: Float
) : PerspectiveProjection, Projection {

    override val matrix: Matrix4f = Matrix4.create()
        get() = ofProjection(
            Math.toRadians(this.fov.toDouble()).toFloat(),
            this.width, this.height, this.near, this.far, field
        )
}
