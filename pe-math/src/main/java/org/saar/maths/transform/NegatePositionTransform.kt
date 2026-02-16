package org.saar.maths.transform

import org.joml.Matrix4fc
import org.jproperty.binding.ObjectBinding
import org.saar.maths.utils.Matrix4
import org.saar.maths.utils.Vector3

class NegatePositionTransform(private val transform: ReadonlyTransform) : ReadonlyTransform {

    private val positionProperty: ObjectBinding<ReadonlyPosition> = object : ObjectBinding<ReadonlyPosition>() {
        private val position: Position = Position.create()

        init {
            bind(transform.position)
        }

        override fun compute(): ReadonlyPosition = this.position.apply {
            set(transform.position.value.negate(Vector3.create()))
        }

        override fun dispose() = unbind(transform.position)
    }

    override val position: ReadonlyPosition get() = this.positionProperty.value

    override val rotation: ReadonlyRotation get() = transform.rotation

    override val scale: ReadonlyScale get() = transform.scale

    private val transformationMatrixProperty: ObjectBinding<Matrix4fc> = object : ObjectBinding<Matrix4fc>() {
        private val matrix = Matrix4.create()

        init {
            bind(positionProperty, rotation, scale)
        }

        override fun compute() = Matrix4.ofTransformation(
            position.value, rotation.value, scale.value, this.matrix)

        override fun dispose() = unbind(positionProperty, rotation, scale)
    }

    override val transformationMatrix: Matrix4fc get() = this.transformationMatrixProperty.value

    override fun toString() = "Transform{$position, $rotation, $scale}"
}