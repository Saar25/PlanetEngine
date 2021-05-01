package org.saar.maths.transform

import org.joml.Matrix4fc

interface Transform : ReadonlyTransform {
    override val transformationMatrix: Matrix4fc
    override val position: Position
    override val rotation: Rotation
    override val scale: Scale
}