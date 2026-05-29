package org.saar.maths.transform

import org.joml.Matrix4f
import org.joml.Matrix4fc
import org.jproperty.ObservableValue
import org.jproperty.binding.ObjectBinding
import org.saar.maths.utils.Matrix4
import org.saar.maths.utils.Vector3

class SimpleTransform : Transform {

    override val position: Position = Position.create()
    override val rotation: Rotation = Rotation.create()
    override val scale: Scale = Scale.create()

    val transformation: ObjectBinding<Matrix4f> = object : ObjectBinding<Matrix4f>() {
        init {
            bind(this@SimpleTransform.position, this@SimpleTransform.rotation, this@SimpleTransform.scale)
        }

        override fun compute(): Matrix4f {
            return Matrix4.ofTransformation(
                this@SimpleTransform.position.value,
                this@SimpleTransform.rotation.value,
                this@SimpleTransform.scale.value,
                Matrix4.create())
        }

        override fun dispose() {
            unbind(this@SimpleTransform.position, this@SimpleTransform.rotation, this@SimpleTransform.scale)
        }
    }

    fun getTransformation(): ObservableValue<Matrix4f> {
        return this.transformation
    }

    override val transformationMatrix: Matrix4fc
        get() = this.transformation.value

    override fun lookAt(position: ReadonlyPosition) {
        val direction = Vector3.sub(
            position.value, position.value)
        rotation.lookAlong(direction)
    }

    override fun toString(): String {
        return "Transform{" + position +
                ", " + rotation +
                ", " + scale + '}'
    }
}
