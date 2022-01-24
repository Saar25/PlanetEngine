package org.saar.minecraft.components

import org.saar.core.common.components.TransformComponent
import org.saar.core.common.components.VelocityComponent
import org.saar.core.node.ComposableNode
import org.saar.core.node.NodeComponent
import org.saar.core.util.Time
import org.saar.minecraft.World
import kotlin.math.floor

class GravityComponent(
    private val world: World,
) : NodeComponent {

    private val time: Time = Time()

    private lateinit var transformComponent: TransformComponent
    private lateinit var velocityComponent: VelocityComponent

    private fun isOnBlock(world: World): Boolean {
        val position = this.transformComponent.transform.position
        val block = world.getBlock(
            floor(position.x.toDouble()).toInt(),
            floor((position.y - 1.51f).toDouble()).toInt(),
            floor(position.z.toDouble()).toInt())
        return block.isCollideable
    }

    override fun start(node: ComposableNode) {
        this.transformComponent = node.components.get()
        this.velocityComponent = node.components.get()
    }

    override fun update(node: ComposableNode) {
        if (isOnBlock(world)) {
            this.velocityComponent.direction.y = 0f
        } else {
            val delta = .98f * this.time.delta().toMillis() / 1000f
            this.velocityComponent.direction.y -= delta
        }

        this.time.update()
    }
}