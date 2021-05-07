package org.saar.core.common.behaviors

import org.joml.Vector3f
import org.saar.core.behavior.Behavior
import org.saar.core.behavior.BehaviorNode
import org.saar.maths.utils.Vector3

class VelocityBehavior : Behavior {

    private lateinit var transformBehavior: TransformBehavior

    val direction: Vector3f = Vector3.create()

    override fun start(node: BehaviorNode) {
        this.transformBehavior = node.behaviors.get(TransformBehavior::class.java)
    }

    override fun update(node: BehaviorNode) {
        this.transformBehavior.transform.position.add(this.direction)
    }
}