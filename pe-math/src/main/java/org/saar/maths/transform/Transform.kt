package org.saar.maths.transform

import org.joml.Matrix4fc
import org.saar.maths.utils.Vector3

interface Transform : ReadonlyTransform {
    override val transformationMatrix: Matrix4fc
    override val position: Position
    override val rotation: Rotation
    override val scale: Scale

    fun lookAt(position: ReadonlyPosition) {
        val direction = Vector3.sub(position.value, this.position.value)
        this.rotation.lookAlong(direction)
    }
}