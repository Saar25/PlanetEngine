package org.saar.maths.transform

import org.joml.Matrix4fc
import org.saar.maths.utils.Matrix4

object NullTransform : ReadonlyTransform {

    override val transformationMatrix: Matrix4fc = Matrix4.create()

    override val position: ReadonlyPosition = Position.create()
    override val rotation: ReadonlyRotation = Rotation.create()
    override val scale: ReadonlyScale = Scale.create()
}