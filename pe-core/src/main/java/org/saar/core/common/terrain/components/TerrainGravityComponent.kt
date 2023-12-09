package org.saar.core.common.terrain.components

import org.saar.core.common.components.AccelerationComponent
import org.saar.core.common.components.TransformComponent
import org.saar.core.common.components.VelocityComponent
import org.saar.core.common.terrain.World
import org.saar.core.node.ComposableNode
import org.saar.core.node.NodeComponent
import org.saar.core.util.Time

class TerrainGravityComponent(private val world: World) : NodeComponent {

    private lateinit var accelerationComponent: AccelerationComponent
    private lateinit var velocityComponent: VelocityComponent
    private lateinit var transformComponent: TransformComponent

    private val timer = Time()

    override fun start(node: ComposableNode) {
        this.accelerationComponent = node.components.get()
        this.velocityComponent = node.components.get()
        this.transformComponent = node.components.get()
    }

    override fun update(node: ComposableNode) {
        val position = this.transformComponent.transform.position
        val height = this.world.getHeight(position.x, position.y, position.z)

        if (position.y > height) {
            this.accelerationComponent.direction.y = -9.8f
        } else {
            this.accelerationComponent.direction.y = 0f
            this.velocityComponent.direction.y =
                this.velocityComponent.direction.y.coerceAtLeast(0f)
        }

        position.y = position.y.coerceAtLeast(height)

        this.timer.update()
    }

}