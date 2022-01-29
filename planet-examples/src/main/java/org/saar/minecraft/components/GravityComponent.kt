package org.saar.minecraft.components

import org.saar.core.common.components.TransformComponent
import org.saar.core.common.components.VelocityComponent
import org.saar.core.node.ComposableNode
import org.saar.core.node.NodeComponent
import org.saar.core.util.Time

class GravityComponent : NodeComponent {

    private val time: Time = Time()

    private lateinit var transformComponent: TransformComponent
    private lateinit var velocityComponent: VelocityComponent
    private lateinit var collisionComponent: CollisionComponent

    override fun start(node: ComposableNode) {
        this.transformComponent = node.components.get()
        this.velocityComponent = node.components.get()
        this.collisionComponent = node.components.get()
    }

    override fun update(node: ComposableNode) {
        if (this.collisionComponent.isOnBlock()) {
            this.velocityComponent.direction.y = 0f
        } else {
            val delta = .98f * this.time.delta().toMillis() / 1000f
            this.velocityComponent.direction.y -= delta
        }

        this.time.update()
    }
}