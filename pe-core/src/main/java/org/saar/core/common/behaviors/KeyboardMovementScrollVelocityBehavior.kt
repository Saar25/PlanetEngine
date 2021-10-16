package org.saar.core.common.behaviors

import org.saar.core.behavior.Behavior
import org.saar.core.behavior.BehaviorNode
import org.saar.lwjgl.glfw.input.mouse.Mouse
import org.saar.maths.utils.Vector3

class KeyboardMovementScrollVelocityBehavior(mouse: Mouse) : Behavior {

    private lateinit var keyboardMovementBehavior: KeyboardMovementBehavior

    private val maxVelocity = Vector3.of(100f)

    private var velocity = 0f

    init {
        mouse.addScrollListener {
            this.velocity += it.offset.toFloat()
        }
    }

    override fun start(node: BehaviorNode) {
        this.keyboardMovementBehavior = node.behaviors.get()
    }

    override fun update(node: BehaviorNode) {
        this.keyboardMovementBehavior.velocity
            .add(this.velocity, this.velocity, this.velocity)
            .max(Vector3.ONE).min(this.maxVelocity)
        this.velocity = 0f
    }
}