package org.saar.core.common.behaviors

import org.joml.Vector3f
import org.saar.core.behavior.Behavior
import org.saar.core.behavior.BehaviorNode
import org.saar.maths.utils.Vector3

class RandomMovementBehavior : Behavior {

    private lateinit var accelerationBehavior: AccelerationBehavior

    val direction: Vector3f = Vector3.create()

    override fun start(node: BehaviorNode) {
        this.accelerationBehavior = node.behaviors.get()
        val randomize = Vector3.randomize(Vector3.create()).mul(1f, 0f, 1f).sub(.5f, 0f, .5f).normalize(.1f)
        this.accelerationBehavior.direction.set(randomize)
    }

    override fun update(node: BehaviorNode) {
        val randomize = Vector3.randomize(Vector3.create()).mul(1f, 0f, 1f).sub(.5f, 0f, .5f).normalize(.03f)
        this.accelerationBehavior.direction.set(randomize)
    }
}