package org.saar.core.common.behaviors

import org.saar.core.behavior.Behavior
import org.saar.core.behavior.BehaviorNode
import org.saar.lwjgl.glfw.input.mouse.Mouse
import org.saar.lwjgl.glfw.input.mouse.MouseButton
import org.saar.maths.JomlOperators.component1
import org.saar.maths.JomlOperators.component2
import org.saar.maths.JomlOperators.component3
import org.saar.maths.utils.Vector3

class SmoothMouseRotationBehavior(private val mouse: Mouse, private val velocity: Float) : Behavior {

    private lateinit var transformBehavior: TransformBehavior

    private val toRotate = Vector3.zero()
    private var xLast: Int = this.mouse.xPos
    private var yLast: Int = this.mouse.yPos

    override fun start(node: BehaviorNode) {
        this.transformBehavior = node.behaviors.get()
    }

    override fun update(node: BehaviorNode) {
        if (this.mouse.isButtonDown(MouseButton.PRIMARY)) {
            this.toRotate.y += this.mouse.xPos - this.xLast
            this.toRotate.x += this.mouse.yPos - this.yLast
            this.toRotate.mul(this.velocity)
        }

        val (x, y, z) = this.toRotate
        this.transformBehavior.transform
            .rotation.rotateDegrees(x, y, z)

        this.toRotate.mul(.95f)
        this.xLast = this.mouse.xPos
        this.yLast = this.mouse.yPos
    }
}