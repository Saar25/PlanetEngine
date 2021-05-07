package org.saar.core.common.behaviors

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
        if (this.keyboard.isKeyPressed('W'.toInt())) {
            this.transformBehavior.transform.position.addZ(this.velocity.z() * this.time.delta().toMillis() / 1000f)
        }
        if (this.keyboard.isKeyPressed('A'.toInt())) {
            this.transformBehavior.transform.position.addX(this.velocity.x() * this.time.delta().toMillis() / 1000f)
        }
        if (this.keyboard.isKeyPressed('S'.toInt())) {
            this.transformBehavior.transform.position.subZ(this.velocity.z() * this.time.delta().toMillis() / 1000f)
        }
        if (this.keyboard.isKeyPressed('D'.toInt())) {
            this.transformBehavior.transform.position.subX(this.velocity.x() * this.time.delta().toMillis() / 1000f)
        }
        if (this.keyboard.isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
            this.transformBehavior.transform.position.addY(this.velocity.y() * this.time.delta().toMillis() / 1000f)
        }
        if (this.keyboard.isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT)) {
            this.transformBehavior.transform.position.subY(this.velocity.y() * this.time.delta().toMillis() / 1000f)
        }

        this.time.update()
    }
}