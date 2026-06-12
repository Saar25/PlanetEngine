package org.saar.maths.transform

import org.joml.Matrix4fc
import org.jproperty.binding.ObjectBinding
import org.saar.maths.objects.Planef
import org.saar.maths.utils.Matrix4
import org.saar.maths.utils.Vector3

class ReflectedTransform(private val transform: ReadonlyTransform, private val plane: Planef) : ReadonlyTransform {

    private val positionProperty: ObjectBinding<ReadonlyPosition> = object : ObjectBinding<ReadonlyPosition>() {
        private val position: Position = Position.create()

        init {
            bind(transform.position)
        }

        override fun compute(): ReadonlyPosition = this.position.apply {
            val normal = Vector3.normalize(plane.a, plane.b, plane.c)

            val p = transform.position.value
            val distance = plane.distance(p.x(), p.y(), p.z())
            val ptc = Vector3.mul(normal, distance * 2)
            set(p.sub(ptc, ptc))
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
            val normal = Vector3.normalize(plane.a, plane.b, plane.c)
            val reflect = transform.rotation.direction.reflect(normal).negate()
            lookAlong(reflect)
        }

        override fun dispose() = unbind(transform.rotation)
    }
    override val rotation: ReadonlyRotation get() = this.rotationProperty.value

    override val scale: ReadonlyScale get() = this.transform.scale

    private val transformationMatrixProperty: ObjectBinding<Matrix4fc> = object : ObjectBinding<Matrix4fc>() {
        private val matrix = Matrix4.create()

        init {
            bind(positionProperty, rotationProperty, scale)
        }

        override fun compute() = Matrix4.ofTransformation(
            position.value, rotation.value, scale.value, this.matrix)

        override fun dispose() = unbind(positionProperty, rotationProperty, scale)
    }
    override val transformationMatrix: Matrix4fc get() = this.transformationMatrixProperty.value

    override fun toString() = "Transform{$position, $rotation, $scale}"
}