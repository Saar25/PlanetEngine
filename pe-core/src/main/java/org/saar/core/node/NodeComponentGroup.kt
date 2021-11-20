package org.saar.core.node

import kotlin.reflect.KClass

class NodeComponentGroup(private val components: List<NodeComponent>) {

    constructor(vararg components: NodeComponent) : this(listOf(*components))

    constructor(group: NodeComponentGroup, vararg components: NodeComponent) : this(group.components + components)

    fun <T : NodeComponent> getNullable(componentClass: Class<T>): T? {
        return componentClass.cast(this.components.find(componentClass::isInstance))
    }

    fun <T : NodeComponent> getNullable(componentClass: KClass<T>): T? {
        return getNullable(componentClass.java)
    }

    fun <T : NodeComponent> get(componentClass: Class<T>): T = getNullable(componentClass)!!

    inline fun <reified T : NodeComponent> getNullable(): T? = getNullable(T::class)

    inline fun <reified T : NodeComponent> get(): T = getNullable()!!

    fun start(node: ComposableNode) = this.components.forEach { it.start(node) }

    fun update(node: ComposableNode) = this.components.forEach { it.update(node) }

    fun delete(node: ComposableNode) = this.components.forEach { it.delete(node) }

}