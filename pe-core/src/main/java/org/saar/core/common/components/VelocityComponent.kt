package org.saar.core.common.components

import org.joml.Vector3f
import org.saar.core.node.NodeComponent
import org.saar.core.node.ComposableNode
import org.saar.maths.utils.Vector3

class VelocityComponent : NodeComponent {

    private lateinit var transformComponent
    : TransformComponent

    val direction: Vector3f = Vector3.create()

    override fun start(node: ComposableNode) {
        this.transformComponent = node.components.get()
    }

    override fun update(node: ComposableNode) {
        this.transformComponent.transform.position.add(this.direction)
    }
}