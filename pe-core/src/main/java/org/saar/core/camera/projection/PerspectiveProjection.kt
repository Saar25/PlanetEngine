package org.saar.core.camera.projection

import org.saar.core.camera.Projection

interface PerspectiveProjection : Projection {

    val fov: Float

    val width: Float

    val height: Float

    val near: Float

    val far: Float
}
