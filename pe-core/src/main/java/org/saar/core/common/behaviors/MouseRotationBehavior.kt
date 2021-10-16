package org.saar.core.common.behaviors

import org.saar.core.behavior.Behavior
import org.saar.core.behavior.BehaviorNode
import org.saar.lwjgl.glfw.input.mouse.Mouse
import org.saar.lwjgl.glfw.input.mouse.MouseButton
import org.saar.maths.utils.Vector3

class MouseRotationBehavior(private val mouse: Mouse, private val velocity: Float) : Behavior {

    private lateinit var transformBehavior: TransformBehavior

    private var xLast: Int = this.mouse.xPos
    private var yLast: Int = this.mouse.yPos

    override fun start(node: BehaviorNode) {
        this.transformBehavior = node.behaviors.get()
    }

    override fun update(node: BehaviorNode) {
        if (this.mouse.isButtonDown(MouseButton.PRIMARY)) {
            val toRotate = Vector3.zero()
            toRotate.y += this.mouse.xPos - this.xLast
            toRotate.x += this.mouse.yPos - this.yLast
            toRotate.mul(this.velocity)

            this.transformBehavior.transform.rotation.rotateDegrees(
                toRotate.x, toRotate.y, toRotate.z)
        }

        this.xLast = this.mouse.xPos
        this.yLast = this.mouse.yPos
    }
}