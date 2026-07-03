package org.saar.core.renderer

interface Renderer<in C : RenderContext, in T> {

    fun render(context: C, models: Iterable<T>)

    fun render(context: C, vararg models: T) = render(context, models.asIterable())

    fun render(context: C, model: T) = render(context, listOf(model))

    fun delete()

}
