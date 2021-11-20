package org.saar.core.common.components

import org.saar.core.node.NodeComponent
import org.saar.core.node.ComposableNode
import org.saar.lwjgl.glfw.input.mouse.Mouse
import org.saar.lwjgl.glfw.input.mouse.MouseButton
import org.saar.maths.utils.Vector3

class MouseRotationComponent(private val mouse: Mouse, private val velocity: Float) : NodeComponent {

    private lateinit var transformComponent: TransformComponent

    private var xLast: Int = this.mouse.xPos
    private var yLast: Int = this.mouse.yPos

    override fun start(node: ComposableNode) {
        this.transformComponent = node.components.get()
    }

    override fun update(node: ComposableNode) {
        if (this.mouse.isButtonDown(MouseButton.PRIMARY)) {
            val toRotate = Vector3.zero()
            toRotate.y += this.mouse.xPos - this.xLast
            toRotate.x += this.mouse.yPos - this.yLast
            toRotate.mul(this.velocity)

            this.transformComponent.transform.rotation.rotateDegrees(
                toRotate.x, toRotate.y, toRotate.z)
        }

        this.xLast = this.mouse.xPos
        this.yLast = this.mouse.yPos
    }
}