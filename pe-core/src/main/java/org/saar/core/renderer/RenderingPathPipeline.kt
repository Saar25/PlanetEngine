package org.saar.core.renderer

import org.saar.core.renderer.renderpass.RenderPass
import org.saar.core.renderer.renderpass.RenderPassBuffers

interface RenderingPathPipeline<T : RenderPassBuffers> {
    val prototype: RenderingPathScreenPrototype<T>
    val passes: Array<out RenderPass<T>>
}

inline fun <E : RenderPassBuffers, reified T : RenderPass<E>> RenderingPathPipeline<E>.findOne(): T? {
    return findOne(T::class.java)
}

inline fun <E : RenderPassBuffers, reified T : RenderPass<E>> RenderingPathPipeline<E>.findMany(): List<T> {
    return findMany(T::class.java)
}

@Suppress("UNCHECKED_CAST")
fun <E : RenderPassBuffers, T : RenderPass<E>> RenderingPathPipeline<E>.findOne(klass: Class<T>): T? {
    return this.passes.find { klass.isInstance(it) } as T?
}

@Suppress("UNCHECKED_CAST")
fun <E : RenderPassBuffers, T : RenderPass<E>> RenderingPathPipeline<E>.findMany(klass: Class<T>): List<T> {
    return this.passes.filter { klass.isInstance(it) }.map { it as T }
}
