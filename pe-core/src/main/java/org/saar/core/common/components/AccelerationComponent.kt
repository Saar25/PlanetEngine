package org.saar.core.common.components

import org.joml.Vector3f
import org.saar.core.node.NodeComponent
import org.saar.core.node.ComposableNode
import org.saar.maths.utils.Vector3

class AccelerationComponent : NodeComponent {

    private lateinit var velocityComponent: VelocityComponent

    val direction: Vector3f = Vector3.create()

    override fun start(node: ComposableNode) {
        this.velocityComponent = node.components.get()
    }

    override fun update(node: ComposableNode) {
        this.velocityComponent.direction.add(this.direction)
    }
}