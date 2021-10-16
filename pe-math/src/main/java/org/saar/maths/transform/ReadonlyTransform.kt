package org.saar.maths.transform

import org.joml.Matrix4fc

interface ReadonlyTransform {
    val transformationMatrix: Matrix4fc
    val position: ReadonlyPosition
    val rotation: ReadonlyRotation
    val scale: ReadonlyScale
}