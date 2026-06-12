package org.saar.core.common.components

import org.joml.Vector3f
import org.saar.core.node.ComposableNode
import org.saar.core.node.NodeComponent
import org.saar.maths.transform.Transform

class SmoothThirdPersonViewComponent(private val toFollow: Transform,
                                     private val distance: Float,
                                     private val scalar: Float = 0.9f) : NodeComponent {

    private lateinit var transformComponent: TransformComponent

    private val temp: Vector3f = Vector3f()

    override fun start(node: ComposableNode) {
        this.transformComponent = node.components.get()
    }

    override fun update(node: ComposableNode) {
        val target = this.transformComponent.transform.rotation.direction
            .normalize(this.distance, this.temp).add(this.toFollow.position.value)
        val position = this.transformComponent.transform.position.value

        this.transformComponent.transform.position.set(target.lerp(position, this.scalar))
    }
}