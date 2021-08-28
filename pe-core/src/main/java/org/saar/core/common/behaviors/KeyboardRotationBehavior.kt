package org.saar.core.common.behaviors

import org.saar.core.behavior.Behavior
import org.saar.core.behavior.BehaviorNode
import org.saar.core.util.Time
import org.saar.lwjgl.glfw.input.keyboard.Keyboard

class KeyboardRotationBehavior(private val keyboard: Keyboard, private val velocity: Float) : Behavior {

    private lateinit var transformBehavior: TransformBehavior

    private val time = Time()

    override fun start(node: BehaviorNode) {
        this.transformBehavior = node.behaviors.get()
    }

    override fun update(node: BehaviorNode) {
        val delta = this.time.delta().toMillis() / 1000f

        var rotation = 0f

        if (this.keyboard.isKeyPressed('Q'.code)) {
            rotation += this.velocity * delta
        }
        if (this.keyboard.isKeyPressed('E'.code)) {
            rotation -= this.velocity * delta
        }

        this.transformBehavior.transform.rotation.rotateDegrees(0f, rotation, 0f)

        this.time.update()
    }
}