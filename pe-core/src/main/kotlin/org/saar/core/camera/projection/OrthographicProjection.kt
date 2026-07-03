package org.saar.core.camera.projection

import org.saar.core.camera.Projection

interface OrthographicProjection : Projection {
    val left: Float

    val right: Float

    val bottom: Float

    val top: Float

    val zNear: Float

    val zFar: Float
}
