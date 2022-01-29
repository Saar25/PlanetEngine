package org.saar.minecraft.components

import org.lwjgl.glfw.GLFW
import org.saar.core.common.components.TransformComponent
import org.saar.core.common.components.VelocityComponent
import org.saar.core.node.ComposableNode
import org.saar.core.node.NodeComponent
import org.saar.lwjgl.glfw.input.keyboard.Keyboard

class PlayerJumpComponent(private val keyboard: Keyboard) : NodeComponent {

    private lateinit var transformComponent: TransformComponent
    private lateinit var velocityComponent: VelocityComponent
    private lateinit var collisionComponent: CollisionComponent

    override fun start(node: ComposableNode) {
        this.transformComponent = node.components.get()
        this.velocityComponent = node.components.get()
        this.collisionComponent = node.components.get()
    }

    override fun update(node: ComposableNode) {
        if (this.keyboard.isKeyPressed(GLFW.GLFW_KEY_SPACE) && this.collisionComponent.isOnBlock()) {
            this.velocityComponent.direction.y = .25f
        }
    }
}