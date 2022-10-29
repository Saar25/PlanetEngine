package org.saar.core.common.terrain.components

import org.joml.Vector3f
import org.saar.core.common.components.TransformComponent
import org.saar.core.common.components.VelocityComponent
import org.saar.core.common.terrain.World
import org.saar.core.node.ComposableNode
import org.saar.core.node.NodeComponent
import org.saar.lwjgl.glfw.input.keyboard.Keyboard

class TerrainWalkingComponent(private val world: World,
                              private val keyboard: Keyboard,
                              private val xVelocity: Float,
                              private val zVelocity: Float) : NodeComponent {

    private lateinit var velocityComponent: VelocityComponent
    private lateinit var transformComponent: TransformComponent

    private val temp = Vector3f()

    override fun start(node: ComposableNode) {
        this.velocityComponent = node.components.get()
        this.transformComponent = node.components.get()
    }

    override fun update(node: ComposableNode) {
        this.temp.zero()

        if (this.keyboard.isKeyPressed('W'.code)) {
            this.temp.z += this.zVelocity
        }
        if (this.keyboard.isKeyPressed('A'.code)) {
            this.temp.x += this.xVelocity
        }
        if (this.keyboard.isKeyPressed('S'.code)) {
            this.temp.z -= this.zVelocity
        }
        if (this.keyboard.isKeyPressed('D'.code)) {
            this.temp.x -= this.xVelocity
        }

        this.temp.rotate(this.transformComponent.transform.rotation.value).mul(-1f)
        this.velocityComponent.direction.x = this.temp.x
        this.velocityComponent.direction.z = this.temp.z

        this.velocityComponent.direction.x = this.velocityComponent
            .direction.x.coerceIn(-this.xVelocity, this.xVelocity)
        this.velocityComponent.direction.z = this.velocityComponent
            .direction.z.coerceIn(-this.zVelocity, this.zVelocity)
    }
}