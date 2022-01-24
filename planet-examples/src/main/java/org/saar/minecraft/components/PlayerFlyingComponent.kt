package org.saar.minecraft.components

import org.lwjgl.glfw.GLFW
import org.saar.core.common.components.TransformComponent
import org.saar.core.common.components.VelocityComponent
import org.saar.core.node.ComposableNode
import org.saar.core.node.NodeComponent
import org.saar.lwjgl.glfw.input.keyboard.Keyboard

class PlayerFlyingComponent(
    private val keyboard: Keyboard,
    private val speed: Float,
) : NodeComponent {

    private lateinit var transformComponent: TransformComponent
    private lateinit var velocityComponent: VelocityComponent

    override fun start(node: ComposableNode) {
        this.transformComponent = node.components.get()
        this.velocityComponent = node.components.get()
    }

    override fun update(node: ComposableNode) {
        this.velocityComponent.direction.y = 0f
        if (this.keyboard.isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
            this.velocityComponent.direction.y += this.speed
        }
        if (this.keyboard.isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT)) {
            this.velocityComponent.direction.y -= this.speed
        }
    }
}