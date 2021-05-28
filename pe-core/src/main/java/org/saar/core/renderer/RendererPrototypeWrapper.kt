package org.saar.core.renderer

abstract class RendererPrototypeWrapper<T>(prototype: RendererPrototype<T>) : Renderer {

    private val helper = RendererPrototypeHelper(prototype)

    fun render(context: RenderContext, models: Iterable<T>) {
        this.helper.render(context, models)
    }

    fun render(context: RenderContext, vararg models: T) {
        render(context, models.asIterable())
    }

    final override fun delete() {
        this.helper.delete()
    }
}