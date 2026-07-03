package org.saar.core.camera.projection

import org.joml.Anglef
import org.saar.core.camera.Projection

interface PerspectiveProjection : Projection {

    val fov: Anglef

    val width: Float

    val height: Float

    val near: Float

    val far: Float
}
