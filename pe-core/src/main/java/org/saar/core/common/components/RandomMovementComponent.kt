package org.saar.core.common.components

import org.joml.Vector3f
import org.saar.core.node.ComposableNode
import org.saar.core.node.NodeComponent
import org.saar.maths.utils.Vector3

class RandomMovementComponent : NodeComponent {

    private lateinit var accelerationComponent: AccelerationComponent

    val direction: Vector3f = Vector3.create()

    override fun start(node: ComposableNode) {
        this.accelerationComponent = node.components.get()
    }

    override fun update(node: ComposableNode) {
        val randomize = Vector3.randomize(Vector3.create()).mul(1f, 0f, 1f).sub(.5f, 0f, .5f).mul(5f)
        this.accelerationComponent.direction.set(randomize)
    }
}