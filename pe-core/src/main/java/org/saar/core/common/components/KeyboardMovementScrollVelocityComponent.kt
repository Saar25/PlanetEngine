package org.saar.core.common.components

import org.saar.core.node.NodeComponent
import org.saar.core.node.ComposableNode
import org.saar.lwjgl.glfw.input.mouse.Mouse
import org.saar.maths.utils.Vector3

class KeyboardMovementScrollVelocityComponent(mouse: Mouse) : NodeComponent {

    private lateinit var keyboardMovementComponent: KeyboardMovementComponent

    private val maxVelocity = Vector3.of(100f)

    private var velocity = 0f

    init {
        mouse.addScrollListener {
            this.velocity += it.offset.toFloat()
        }
    }

    override fun start(node: ComposableNode) {
        this.keyboardMovementComponent = node.components.get()
    }

    override fun update(node: ComposableNode) {
        this.keyboardMovementComponent.velocity
            .add(this.velocity, this.velocity, this.velocity)
            .max(Vector3.ONE).min(this.maxVelocity)
        this.velocity = 0f
    }
}