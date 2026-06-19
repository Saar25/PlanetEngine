package org.saar.core.camera.projection

import org.joml.Matrix4f
import org.saar.core.camera.Projection
import org.saar.maths.Angle
import org.saar.maths.utils.Matrix4

class SimplePerspectiveProjection(
    override var fov: Angle,
    override var width: Float,
    override var height: Float,
    override var near: Float,
    override var far: Float
) : PerspectiveProjection, Projection {

    override val matrix: Matrix4f = Matrix4.ofProjection(
        this.fov.radians,
        this.width,
        this.height,
        this.near,
        this.far,
        Matrix4.create()
    )
}
