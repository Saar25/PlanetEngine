package org.saar.minecraft.components

import org.joml.Vector3ic
import org.saar.core.common.components.TransformComponent
import org.saar.core.common.components.VelocityComponent
import org.saar.core.node.ComposableNode
import org.saar.core.node.NodeComponent
import org.saar.minecraft.World
import org.saar.minecraft.entity.HitBox

class CollisionComponent(
    private val world: World,
    private val hitBox: HitBox,
) : NodeComponent {

    private lateinit var transformComponent: TransformComponent
    private lateinit var velocityComponent: VelocityComponent

    override fun start(node: ComposableNode) {
        this.transformComponent = node.components.get()
        this.velocityComponent = node.components.get()
    }

    override fun update(node: ComposableNode) {
        val position = this.transformComponent.transform.position
        val direction = this.velocityComponent.direction

        val ensured = this.hitBox.collideWithWorld(this.world, position, direction)

        this.velocityComponent.direction.set(ensured)
    }

    fun isCollidingBlock(block: Vector3ic): Boolean {
        val position = this.transformComponent.transform.position
        return this.hitBox.isCollidingBlock(position, block)
    }
}