package org.saar.core.common.behaviors

import org.joml.Vector3f
import org.saar.core.behavior.Behavior
import org.saar.core.behavior.BehaviorNode
import org.saar.maths.utils.Vector3

class AccelerationBehavior : Behavior {

    private lateinit var velocityBehavior: VelocityBehavior

    val direction: Vector3f = Vector3.create()

    override fun start(node: BehaviorNode) {
        this.velocityBehavior = node.behaviors.get(VelocityBehavior::class.java)
    }

    override fun update(node: BehaviorNode) {
        this.velocityBehavior.direction.add(this.direction)
    }
}