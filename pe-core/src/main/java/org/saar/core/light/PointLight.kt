package org.saar.core.light

import org.joml.Vector3f
import org.saar.core.behavior.BehaviorGroup
import org.saar.core.behavior.BehaviorNode
import org.saar.core.common.behaviors.TransformBehavior
import org.saar.core.node.Node
import org.saar.maths.utils.Vector3

class PointLight(override val behaviors: BehaviorGroup = BehaviorGroup()) : IPointLight, BehaviorNode, Node {
    override val position: Vector3f = Vector3.create()
    override var attenuation: Attenuation = Attenuation.DISTANCE_7
    override val colour: Vector3f = Vector3.create()

    init {
        this.behaviors.start(this)
    }

    override fun update() {
        this.behaviors.update(this)
        this.behaviors.getNullable(TransformBehavior::class)?.also {
            this.position.set(it.transform.position.value)
        }
    }

    override fun delete() {
        this.behaviors.delete(this)
    }
}