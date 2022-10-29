package org.saar.core.node

import kotlin.reflect.KClass

class NodeComponentGroup(components: List<NodeComponent>) {

    private val uninitialized = components.toMutableList()
    private val components = mutableListOf<NodeComponent>()

    constructor(vararg components: NodeComponent) : this(listOf(*components))

    constructor(group: NodeComponentGroup, vararg components: NodeComponent) :
            this(group.components + group.uninitialized + components)

    fun <T : NodeComponent> getNullable(componentClass: Class<T>): T? {
        return componentClass.cast(this.components.find(componentClass::isInstance)
            ?: this.uninitialized.find(componentClass::isInstance))
    }

    fun <T : NodeComponent> getNullable(componentClass: KClass<T>): T? = getNullable(componentClass.java)

    fun <T : NodeComponent> get(componentClass: Class<T>): T = getNullable(componentClass)!!

    inline fun <reified T : NodeComponent> getNullable(): T? = getNullable(T::class)

    inline fun <reified T : NodeComponent> getOrCreate(create: () -> T): T =
        getNullable() ?: create().also { this.add(it) }

    inline fun <reified T : NodeComponent> get(): T = getNullable()!!

    fun add(component: NodeComponent) = this.uninitialized.add(component)

    fun update(node: ComposableNode) {
        val copy = this.uninitialized.toList()
        this.uninitialized.clear()
        this.components += copy
        copy.forEach { it.start(node) }

        this.components.forEach { it.update(node) }
    }

    fun delete(node: ComposableNode) = this.components.forEach { it.delete(node) }

}