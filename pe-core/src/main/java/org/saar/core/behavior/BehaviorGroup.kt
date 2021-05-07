package org.saar.core.behavior

import kotlin.reflect.KClass

class BehaviorGroup(private val behaviors: List<Behavior>) {

    constructor(vararg behaviors: Behavior) : this(listOf(*behaviors))

    @SuppressWarnings("unchecked")
    fun <T : Behavior> getNullable(behaviorClass: Class<T>): T? {
        return behaviorClass.cast(this.behaviors.find(behaviorClass::isInstance))
    }

    @SuppressWarnings("unchecked")
    fun <T : Behavior> getNullable(behaviorClass: KClass<T>): T? {
        return getNullable(behaviorClass.java)
    }

    fun <T : Behavior> get(behaviorClass: Class<T>): T = getNullable(behaviorClass)!!

    inline fun <reified T : Behavior> getNullable(): T? = getNullable(T::class)

    inline fun <reified T : Behavior> get(): T = getNullable()!!

    fun start(node: BehaviorNode) = this.behaviors.forEach { it.start(node) }

    fun update(node: BehaviorNode) = this.behaviors.forEach { it.update(node) }

    fun delete(node: BehaviorNode) = this.behaviors.forEach { it.delete(node) }

}