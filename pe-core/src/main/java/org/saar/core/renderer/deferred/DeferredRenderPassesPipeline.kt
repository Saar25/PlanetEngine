package org.saar.core.renderer.deferred

class DeferredRenderPassesPipeline(vararg val renderPasses: DeferredRenderPass)

inline fun <reified T : DeferredRenderPass> DeferredRenderPassesPipeline.findOne() = findOne(T::class.java)

inline fun <reified T : DeferredRenderPass> DeferredRenderPassesPipeline.findMany() = findMany(T::class.java)

@Suppress("UNCHECKED_CAST")
fun <T : DeferredRenderPass> DeferredRenderPassesPipeline.findOne(klass: Class<T>): T? {
    return this.renderPasses.find { klass.isInstance(it) } as T?
}

@Suppress("UNCHECKED_CAST")
fun <T : DeferredRenderPass> DeferredRenderPassesPipeline.findMany(klass: Class<T>): List<T> {
    return this.renderPasses.filter { klass.isInstance(it) }.map { it as T }
}