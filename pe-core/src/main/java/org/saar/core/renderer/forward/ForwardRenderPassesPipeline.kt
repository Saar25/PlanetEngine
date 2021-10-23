package org.saar.core.renderer.forward

class ForwardRenderPassesPipeline(vararg val renderPasses: ForwardRenderPass)

inline fun <reified T : ForwardRenderPass> ForwardRenderPassesPipeline.findOne() = findOne(T::class.java)

inline fun <reified T : ForwardRenderPass> ForwardRenderPassesPipeline.findMany() = findMany(T::class.java)

@Suppress("UNCHECKED_CAST")
fun <T : ForwardRenderPass> ForwardRenderPassesPipeline.findOne(klass: Class<T>): T? {
    return this.renderPasses.find { klass.isInstance(it) } as T?
}

@Suppress("UNCHECKED_CAST")
fun <T : ForwardRenderPass> ForwardRenderPassesPipeline.findMany(klass: Class<T>): List<T> {
    return this.renderPasses.filter { klass.isInstance(it) }.map { it as T }
}