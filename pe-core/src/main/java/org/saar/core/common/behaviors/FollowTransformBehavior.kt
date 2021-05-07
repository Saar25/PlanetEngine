package org.saar.core.common.behaviors

import org.joml.Vector3f
import org.saar.core.behavior.Behavior
import org.saar.core.behavior.BehaviorNode
import org.saar.maths.transform.Transform
import org.saar.maths.utils.Vector3

class FollowTransformBehavior(private val toFollow: Transform, private val distance: Float) : Behavior {

    private lateinit var transformBehavior: TransformBehavior

    val direction: Vector3f = Vector3.create()

    override fun start(node: BehaviorNode) {
        this.transformBehavior = node.behaviors.get()
    }

    override fun update(node: BehaviorNode) {
        val position = this.transformBehavior.transform.rotation.direction
            .normalize(this.distance).add(this.toFollow.position.value)
        this.transformBehavior.transform.position.set(position)
    }
}