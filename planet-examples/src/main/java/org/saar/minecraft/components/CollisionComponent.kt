package org.saar.minecraft.components

import org.joml.Vector3f
import org.joml.Vector3ic
import org.saar.core.common.components.TransformComponent
import org.saar.core.node.ComposableNode
import org.saar.core.node.NodeComponent
import org.saar.maths.transform.Position
import org.saar.minecraft.World
import org.saar.minecraft.entity.HitBox

class CollisionComponent(
    private val world: World,
    private val hitBox: HitBox,
) : NodeComponent {

    private lateinit var transformComponent: TransformComponent

    private val temp = Vector3f()
    private val previousPosition = Position.create()

    override fun start(node: ComposableNode) {
        this.transformComponent = node.components.get()

        this.previousPosition.set(this.transformComponent.transform.position)
    }

    override fun update(node: ComposableNode) {
        val position = this.transformComponent.transform.position
        val direction = position.value.sub(this.previousPosition.value, this.temp)

        val ensured = this.hitBox.collideWithWorld(this.world, this.previousPosition, direction)

        this.transformComponent.transform.position.set(ensured.add(this.previousPosition.value))

        this.previousPosition.set(this.transformComponent.transform.position)
    }

    fun isCollidingBlock(block: Vector3ic): Boolean {
        val position = this.transformComponent.transform.position
        return this.hitBox.isCollidingBlock(position, block)
    }

    fun isInsideWater(): Boolean {
        val position = this.transformComponent.transform.position
        return this.hitBox.isInsideWater(this.world, position)
    }

    fun isOnBlock(): Boolean {
        val position = this.transformComponent.transform.position
        return this.hitBox.isOnBlock(this.world, position)
    }
}