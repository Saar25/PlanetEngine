package org.saar.core.renderer

interface Renderer<in T> {

    fun render(context: RenderContext, models: Iterable<T>)

    fun render(context: RenderContext, vararg models: T) = render(context, models.asIterable())

    fun render(context: RenderContext, model: T) = render(context, listOf(model))

    fun delete()

}
