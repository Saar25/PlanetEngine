package org.saar.core.common.components

import org.joml.Vector3f
import org.joml.Vector3fc
import org.lwjgl.glfw.GLFW
import org.saar.core.node.NodeComponent
import org.saar.core.node.ComposableNode
import org.saar.core.util.Time
import org.saar.lwjgl.glfw.input.keyboard.Keyboard
import org.saar.maths.utils.Vector3

class KeyboardMovementComponent(private val keyboard: Keyboard, velocity: Vector3fc) : NodeComponent {

    constructor(keyboard: Keyboard, x: Float, y: Float, z: Float) : this(keyboard, Vector3.of(x, y, z))

    private lateinit var transformComponent: TransformComponent

    val velocity: Vector3f = Vector3.of(velocity)

    private val time = Time()

    override fun start(node: ComposableNode) {
        this.transformComponent = node.components.get()
    }

    override fun update(node: ComposableNode) {
        val delta = this.time.delta().toMillis() / 1000f

        val direction: Vector3f = Vector3.create()

        if (this.keyboard.isKeyPressed('W'.code)) {
            direction.z += this.velocity.z() * delta
        }
        if (this.keyboard.isKeyPressed('A'.code)) {
            direction.x += this.velocity.x() * delta
        }
        if (this.keyboard.isKeyPressed('S'.code)) {
            direction.z -= this.velocity.z() * delta
        }
        if (this.keyboard.isKeyPressed('D'.code)) {
            direction.x -= this.velocity.x() * delta
        }

        direction.rotate(this.transformComponent.transform.rotation.value).mul(-1f)

        if (this.keyboard.isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
            direction.y += this.velocity.y() * delta
        }
        if (this.keyboard.isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT)) {
            direction.y -= this.velocity.y() * delta
        }

        this.transformComponent.transform.position.add(direction)

        this.time.update()
    }
}