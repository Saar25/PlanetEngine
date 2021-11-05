package org.saar.core.camera

import org.joml.Matrix4fc
import org.saar.maths.transform.Transform

interface ICamera {
    val transform: Transform
    val projection: Projection
    val viewMatrix: Matrix4fc
}