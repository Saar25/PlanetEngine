package org.saar.core.behavior

class BehaviorGroup {

    val behaviors = mutableListOf<Behavior>()

    fun start(node: BehaviorNode) {
        this.behaviors.forEach { it.start(node) }
    }

    fun update(node: BehaviorNode) {
        this.behaviors.forEach { it.update(node) }
    }

}