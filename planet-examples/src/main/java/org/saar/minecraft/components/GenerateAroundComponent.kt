package org.saar.minecraft.components

import org.saar.core.common.components.TransformComponent
import org.saar.core.node.ComposableNode
import org.saar.core.node.NodeComponent
import org.saar.maths.transform.Position
import org.saar.minecraft.World
import kotlin.math.abs

class GenerateAroundComponent(
    private val world: World,
    private val radius: Int,
) : NodeComponent {

    private val lastPosition = Position.create()

    private lateinit var transformComponent: TransformComponent

    override fun start(node: ComposableNode) {
        this.transformComponent = node.components.get()

        this.lastPosition.set(this.transformComponent.transform.position)
        this.world.generateAround(this.lastPosition, this.radius)
    }

    override fun update(node: ComposableNode) {
        val transform = this.transformComponent.transform
        val xChange: Float = this.lastPosition.x - transform.position.x
        val zChange: Float = this.lastPosition.z - transform.position.z

        if (abs(xChange) > this.radius * 2 || abs(zChange) > this.radius * 2) {
            this.lastPosition.set(transform.position)

            this.world.generateAround(this.lastPosition, this.radius)
        }
    }
}