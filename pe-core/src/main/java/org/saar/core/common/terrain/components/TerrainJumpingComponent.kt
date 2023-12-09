package org.saar.core.common.terrain.components

import org.lwjgl.glfw.GLFW
import org.saar.core.common.components.TransformComponent
import org.saar.core.common.components.VelocityComponent
import org.saar.core.common.terrain.World
import org.saar.core.node.ComposableNode
import org.saar.core.node.NodeComponent
import org.saar.lwjgl.glfw.input.keyboard.Keyboard

class TerrainJumpingComponent(private val world: World,
                              private val keyboard: Keyboard,
                              private val velocity: Float) : NodeComponent {

    private lateinit var velocityComponent: VelocityComponent
    private lateinit var transformComponent: TransformComponent

    override fun start(node: ComposableNode) {
        this.velocityComponent = node.components.get()
        this.transformComponent = node.components.get()
    }

    override fun update(node: ComposableNode) {
        val position = this.transformComponent.transform.position
        val height = this.world.getHeight(position.x, position.y, position.z)

        if (position.y <= height && this.keyboard.isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
            this.velocityComponent.direction.y = this.velocity
        }
    }
}