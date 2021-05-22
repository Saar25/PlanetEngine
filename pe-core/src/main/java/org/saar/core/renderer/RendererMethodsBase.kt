package org.saar.core.renderer

interface RendererMethodsBase<C, M> : Renderer {

    fun render(context: C, models: Iterable<M>)

    fun render(context: C, vararg models: M) {
        render(context, models.asIterable())
    }

    fun render(context: C, model: M) {
        render(context, listOf(model))
    }

}