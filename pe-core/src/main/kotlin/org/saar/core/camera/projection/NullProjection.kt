package org.saar.core.camera.projection

import org.joml.Matrix4fc
import org.saar.core.camera.Projection
import org.saar.maths.utils.Matrix4

object NullProjection : Projection {
    override val matrix: Matrix4fc = Matrix4.create()
}
