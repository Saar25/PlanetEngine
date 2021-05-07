package org.saar.core.common.behaviors

import org.joml.Vector3f
import org.joml.Vector3fc
import org.lwjgl.glfw.GLFW
import org.saar.core.behavior.Behavior
import org.saar.core.behavior.BehaviorNode
import org.saar.core.util.Time
import org.saar.lwjgl.glfw.input.keyboard.Keyboard
import org.saar.maths.utils.Vector3

class KeyboardMovementBehavior(private val keyboard: Keyboard, private val velocity: Vector3fc) : Behavior {

    constructor(keyboard: Keyboard, x: Float, y: Float, z: Float) : this(keyboard, Vector3.of(x, y, z))

    private lateinit var transformBehavior: TransformBehavior

    private val time = Time()

    override fun start(node: BehaviorNode) {
        this.transformBehavior = node.behaviors.get()
    }

    override fun update(node: BehaviorNode) {
        val delta = this.time.delta().toMillis() / 1000f

        val direction: Vector3f = Vector3.create()

        if (this.keyboard.isKeyPressed('W'.toInt())) {
            direction.z += this.velocity.z() * delta
        }
        if (this.keyboard.isKeyPressed('A'.toInt())) {
            direction.x += this.velocity.x() * delta
        }
        if (this.keyboard.isKeyPressed('S'.toInt())) {
            direction.z -= this.velocity.z() * delta
        }
        if (this.keyboard.isKeyPressed('D'.toInt())) {
            direction.x -= this.velocity.x() * delta
        }

        direction.rotate(this.transformBehavior.transform.rotation.value).mul(-1f)

        if (this.keyboard.isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
            direction.y += this.velocity.y() * delta
        }
        if (this.keyboard.isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT)) {
            direction.y -= this.velocity.y() * delta
        }

        this.transformBehavior.transform.position.add(direction)

        this.time.update()
    }
}