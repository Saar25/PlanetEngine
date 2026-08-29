package org.saar.core.camera

import org.joml.Matrix4fc
import org.saar.maths.transform.ReadonlyTransform

interface ICamera {
    val transform: ReadonlyTransform
    val projection: Projection
    val viewMatrix: Matrix4fc
}