package org.saar.minecraft.components

import org.saar.core.common.components.VelocityComponent
import org.saar.core.node.ComposableNode
import org.saar.core.node.NodeComponent
import kotlin.math.max

class WaterFloatingComponent : NodeComponent {

    private lateinit var velocityComponent: VelocityComponent
    private lateinit var collisionComponent: CollisionComponent

    override fun start(node: ComposableNode) {
        this.velocityComponent = node.components.get()
        this.collisionComponent = node.components.get()
    }

    override fun update(node: ComposableNode) {
        if (this.collisionComponent.isInsideWater()) {
            this.velocityComponent.direction.y -= .01f
            this.velocityComponent.direction.y = max(this.velocityComponent.direction.y, -.05f)
        }
    }
}