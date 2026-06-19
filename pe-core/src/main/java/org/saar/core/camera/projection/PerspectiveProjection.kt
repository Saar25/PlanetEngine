package org.saar.core.camera.projection

import org.saar.core.camera.Projection
import org.saar.maths.Angle

interface PerspectiveProjection : Projection {

    val fov: Angle

    val width: Float

    val height: Float

    val near: Float

    val far: Float
}
