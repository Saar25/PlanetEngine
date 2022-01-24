package org.saar.minecraft.components

import org.lwjgl.glfw.GLFW
import org.saar.core.common.components.TransformComponent
import org.saar.core.common.components.VelocityComponent
import org.saar.core.node.ComposableNode
import org.saar.core.node.NodeComponent
import org.saar.lwjgl.glfw.input.keyboard.Keyboard
import org.saar.minecraft.World
import kotlin.math.floor

class PlayerJumpComponent(
    private val world: World,
    private val keyboard: Keyboard,
) : NodeComponent {

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
        if (this.keyboard.isKeyPressed(GLFW.GLFW_KEY_SPACE) && isOnBlock(world)) {
            this.velocityComponent.direction.y = .25f
        }
    }
}