package org.saar.maths.transform

import org.joml.Matrix4fc
import org.jproperty.binding.ObjectBinding
import org.saar.maths.utils.Matrix4
import org.saar.maths.utils.Quaternion
import org.saar.maths.utils.Vector3

class InvertedTransform(private val transform: ReadonlyTransform) : ReadonlyTransform {

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

    private val rotationProperty: ObjectBinding<ReadonlyRotation> = object : ObjectBinding<ReadonlyRotation>() {
        private val rotation: Rotation = Rotation.create()

        init {
            bind(transform.rotation)
        }

        override fun compute(): ReadonlyRotation = this.rotation.apply {
            set(transform.rotation.value.invert(Quaternion.create()))
        }

        override fun dispose() = unbind(transform.rotation)
    }

    override val rotation: ReadonlyRotation get() = this.rotationProperty.value

    private val scaleProperty: ObjectBinding<ReadonlyScale> = object : ObjectBinding<ReadonlyScale>() {
        private val scale: Scale = Scale.create()

        init {
            bind(transform.scale)
        }

        override fun compute(): ReadonlyScale = this.scale.apply {
            set(Vector3.of(1f).div(transform.scale.value))
        }

        override fun dispose() = unbind(transform.scale)
    }

    override val scale: ReadonlyScale get() = this.scaleProperty.value

    private val transformationMatrixProperty: ObjectBinding<Matrix4fc> = object : ObjectBinding<Matrix4fc>() {
        private val matrix = Matrix4.create()

        init {
            bind(positionProperty, rotationProperty, scaleProperty)
        }

        override fun compute() = Matrix4.ofTransformation(
            position.value, rotation.value, scale.value, this.matrix)

        override fun dispose() = unbind(positionProperty, rotationProperty, scaleProperty)
    }
    override val transformationMatrix: Matrix4fc get() = this.transformationMatrixProperty.value

    override fun toString() = "Transform{$position, $rotation, $scale}"
}