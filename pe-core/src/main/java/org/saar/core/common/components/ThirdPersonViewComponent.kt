package org.saar.core.common.components

import org.saar.core.node.NodeComponent
import org.saar.core.node.ComposableNode
import org.saar.maths.transform.Transform

class ThirdPersonViewComponent(private val toFollow: Transform, private val distance: Float) : NodeComponent {

    private lateinit var transformComponent: TransformComponent

    override fun start(node: ComposableNode) {
        this.transformComponent = node.components.get()
    }

    override fun update(node: ComposableNode) {
        val position = this.transformComponent.transform.rotation.direction
            .normalize(this.distance).add(this.toFollow.position.value)
        this.transformComponent.transform.position.set(position)
    }
}