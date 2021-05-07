package org.saar.core.behavior

class BehaviorGroup(private val behaviors: List<Behavior>) {

    constructor(vararg behaviors: Behavior) : this(listOf(*behaviors))

    @SuppressWarnings("unchecked")
    fun <T : Behavior> getNullable(behaviorClass: Class<T>): T? {
        return behaviorClass.cast(this.behaviors.find(behaviorClass::isInstance))
    }

    fun <T : Behavior> get(behaviorClass: Class<T>): T = getNullable(behaviorClass)!!

    fun start(node: BehaviorNode) = this.behaviors.forEach { it.start(node) }

    fun update(node: BehaviorNode) = this.behaviors.forEach { it.update(node) }

    fun delete(node: BehaviorNode) = this.behaviors.forEach { it.delete(node) }

}