package org.saar.core.common.components

import org.joml.Vector3f
import org.saar.core.node.ComposableNode
import org.saar.core.node.NodeComponent
import org.saar.maths.JomlOperators.component1
import org.saar.maths.JomlOperators.component2
import org.saar.maths.JomlOperators.component3
import org.saar.maths.transform.Transform

class BackFaceComponent @JvmOverloads constructor(
    private val relative: Transform,
    private val minVelocity: Float = 0f
) : NodeComponent {

    private lateinit var transformComponent: TransformComponent
    private lateinit var velocityComponent: VelocityComponent

    private val temp: Vector3f = Vector3f()

    override fun start(node: ComposableNode) {
        this.transformComponent = node.components.get()
        this.velocityComponent = node.components.get()
    }

    override fun update(node: ComposableNode) {
        val (x, y, z) = this.velocityComponent.direction.absolute(this.temp)
        if (x > this.minVelocity || y > this.minVelocity || z > this.minVelocity) {
            val position = this.transformComponent.transform.position.value
            val direction = position.sub(this.relative.position.value, this.temp).mul(1f, 0f, 1f)
            this.transformComponent.transform.rotation.lookAlong(direction)
        }
    }
}