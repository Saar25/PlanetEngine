package org.saar.maths.transform

import org.joml.Matrix4fc
import org.saar.maths.utils.Matrix4
import org.saar.maths.utils.Quaternion
import org.saar.maths.utils.Vector3

class RelativeTransform(
    private val transform: ReadonlyTransform,
    private val from: ReadonlyTransform,
    private val to: ReadonlyTransform
) : ReadonlyTransform {

    private val position0 = Position.create()
    override val position: ReadonlyPosition
        get() = position0.also {
            it.set(transformationMatrix.getTranslation(Vector3.create()))
        }

    private val rotation0 = Rotation.create()
    override val rotation: ReadonlyRotation
        get() = rotation0.also {
            it.set(transformationMatrix.getNormalizedRotation(Quaternion.create()))
        }

    private val scale0 = Scale.create()
    override val scale: ReadonlyScale
        get() = scale0.also {
            it.set(transformationMatrix.getScale(Vector3.create()))
        }

    override val transformationMatrix: Matrix4fc
        get() {
            val transformMatrix = transform.transformationMatrix
            val fromMatrix = from.transformationMatrix
            val toMatrix = to.transformationMatrix

            val relativeToEntrance = fromMatrix.invert(Matrix4.create()).mul(transformMatrix)
            return toMatrix.mul(relativeToEntrance, relativeToEntrance)
        }

    override fun toString() = "Transform{$position, $rotation, $scale}"
}