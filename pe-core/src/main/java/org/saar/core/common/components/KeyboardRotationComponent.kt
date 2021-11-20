package org.saar.core.common.components

import org.saar.core.node.NodeComponent
import org.saar.core.node.ComposableNode
import org.saar.core.util.Time
import org.saar.lwjgl.glfw.input.keyboard.Keyboard

class KeyboardRotationComponent(private val keyboard: Keyboard, private val velocity: Float) : NodeComponent {

    private lateinit var transformComponent: TransformComponent

    private val time = Time()

    override fun start(node: ComposableNode) {
        this.transformComponent = node.components.get()
    }

    override fun update(node: ComposableNode) {
        val delta = this.time.delta().toMillis() / 1000f

        var rotation = 0f

        if (this.keyboard.isKeyPressed('Q'.code)) {
            rotation += this.velocity * delta
        }
        if (this.keyboard.isKeyPressed('E'.code)) {
            rotation -= this.velocity * delta
        }

        this.transformComponent.transform.rotation.rotateDegrees(0f, rotation, 0f)

        this.time.update()
    }
}