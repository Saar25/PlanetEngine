package org.saar.minecraft.components

import org.lwjgl.glfw.GLFW
import org.saar.core.common.components.TransformComponent
import org.saar.core.common.components.VelocityComponent
import org.saar.core.node.ComposableNode
import org.saar.core.node.NodeComponent
import org.saar.lwjgl.glfw.input.keyboard.Keyboard
import org.saar.maths.utils.Vector3

class PlayerWalkingComponent(
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
        val speed = if (this.keyboard.isKeyPressed(GLFW.GLFW_KEY_LEFT_CONTROL)) this.speed * 5 else this.speed

        val direction = Vector3.create()
        if (this.keyboard.isKeyPressed('W'.code)) {
            direction.add(0f, 0f, -1f)
        }
        if (this.keyboard.isKeyPressed('A'.code)) {
            direction.add(-1f, 0f, 0f)
        }
        if (this.keyboard.isKeyPressed('S'.code)) {
            direction.add(0f, 0f, +1f)
        }
        if (this.keyboard.isKeyPressed('D'.code)) {
            direction.add(+1f, 0f, 0f)
        }

        direction.rotate(this.transformComponent.transform.rotation.value)
        direction.y = 0f

        if (direction.lengthSquared() > 0) {
            direction.normalize(speed)
        }

        this.velocityComponent.direction.mul(0f, 1f, 0f)
        this.velocityComponent.direction.add(direction)
    }
}