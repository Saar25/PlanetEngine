package org.saar.maths.transform

import org.joml.Matrix4fc
import org.joml.primitives.Planef
import org.jproperty.binding.ObjectBinding
import org.saar.maths.utils.Matrix4
import org.saar.maths.utils.Matrix4.ofTransformation
import org.saar.maths.utils.Vector3

class ReflectedTransform(private val transform: ReadonlyTransform, private val plane: Planef) : ReadonlyTransform {

    private val positionProperty: ObjectBinding<ReadonlyPosition> = object : ObjectBinding<ReadonlyPosition>() {
        private val position: Position = Position.create()

        init {
            this.bind(this@ReflectedTransform.transform.position)
        }

        override fun compute(): ReadonlyPosition = this.position.apply {
            val normal = Vector3.normalize(
                this@ReflectedTransform.plane.a,
                this@ReflectedTransform.plane.b,
                this@ReflectedTransform.plane.c
            )

            val p = this@ReflectedTransform.transform.position.value
            val distance = this@ReflectedTransform.plane.distance(p.x(), p.y(), p.z())
            val ptc = Vector3.mul(normal, distance * 2)
            this.set(p.sub(ptc, ptc))
        }

        override fun dispose() = this.unbind(this@ReflectedTransform.transform.position)
    }

    override val position: ReadonlyPosition get() = this.positionProperty.value

    private val rotationProperty: ObjectBinding<ReadonlyRotation> = object : ObjectBinding<ReadonlyRotation>() {
        private val rotation: Rotation = Rotation.create()

        init {
            this.bind(this@ReflectedTransform.transform.rotation)
        }

        override fun compute(): ReadonlyRotation = this.rotation.apply {
            val normal = Vector3.normalize(
                this@ReflectedTransform.plane.a,
                this@ReflectedTransform.plane.b,
                this@ReflectedTransform.plane.c
            )
            val reflect = this@ReflectedTransform.transform.rotation.direction.reflect(normal).negate()
            this.lookAlong(reflect)
        }

        override fun dispose() = this.unbind(this@ReflectedTransform.transform.rotation)
    }
    override val rotation: ReadonlyRotation get() = this.rotationProperty.value

    override val scale: ReadonlyScale get() = this.transform.scale

    private val transformationMatrixProperty: ObjectBinding<Matrix4fc> = object : ObjectBinding<Matrix4fc>() {
        private val matrix = Matrix4.create()

        init {
            this.bind(
                this@ReflectedTransform.positionProperty,
                this@ReflectedTransform.rotationProperty,
                this@ReflectedTransform.scale
            )
        }

        override fun compute() = this.matrix.ofTransformation(
            this@ReflectedTransform.position.value,
            this@ReflectedTransform.rotation.value,
            this@ReflectedTransform.scale.value
        )

        override fun dispose() =
            this.unbind(
                this@ReflectedTransform.positionProperty,
                this@ReflectedTransform.rotationProperty,
                this@ReflectedTransform.scale
            )
    }
    override val transformationMatrix: Matrix4fc get() = this.transformationMatrixProperty.value

    override fun toString() = "Transform{${this.position}, ${this.rotation}, ${this.scale}}"
}