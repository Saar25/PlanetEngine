package org.saar.maths.transform

import org.joml.Matrix4fc
import org.jproperty.binding.ObjectBinding
import org.saar.maths.JomlOperators.component1
import org.saar.maths.JomlOperators.component2
import org.saar.maths.JomlOperators.component3
import org.saar.maths.utils.Matrix4
import org.saar.maths.utils.Quaternion
import org.saar.maths.utils.Vector3

class ComposedTransform(private vararg val transforms: ReadonlyTransform) : ReadonlyTransform {

    private val positionProperty: ObjectBinding<ReadonlyPosition> = object : ObjectBinding<ReadonlyPosition>() {
        private val position: Position = Position.create()

        init {
            bind(*transforms.map { it.position }.toTypedArray())
        }

        override fun compute(): ReadonlyPosition = this.position.apply {
            set(0f, 0f, 0f)
            transforms.forEach {
                val (x, y, z) = it.position.value
                add(x, y, z)
            }
        }

        override fun dispose() = unbind(*transforms.map { it.position }.toTypedArray())
    }

    override val position: ReadonlyPosition get() = this.positionProperty.value

    private val rotationProperty: ObjectBinding<ReadonlyRotation> = object : ObjectBinding<ReadonlyRotation>() {
        private val rotation: Rotation = Rotation.create()

        init {
            bind(*transforms.map { it.rotation }.toTypedArray())
        }

        override fun compute(): ReadonlyRotation = this.rotation.apply {
            val rotation = transforms.fold(Quaternion.create()) { quaternion, transform ->
                quaternion.mul(transform.rotation.value)
            }
            set(rotation)
        }

        override fun dispose() = unbind(*transforms.map { it.rotation }.toTypedArray())
    }

    override val rotation: ReadonlyRotation get() = this.rotationProperty.value

    private val scaleProperty: ObjectBinding<ReadonlyScale> = object : ObjectBinding<ReadonlyScale>() {
        private val scale: Scale = Scale.create()

        init {
            bind(*transforms.map { it.scale }.toTypedArray())
        }

        override fun compute(): ReadonlyScale = this.scale.apply {
            val scale = transforms.fold(Vector3.of(1f)) { scale, transform ->
                scale.mul(transform.scale.value)
            }
            set(scale)
        }

        override fun dispose() = unbind(*transforms.map { it.scale }.toTypedArray())
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