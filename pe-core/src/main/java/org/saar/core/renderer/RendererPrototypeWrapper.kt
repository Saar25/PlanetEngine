package org.saar.core.renderer

abstract class RendererPrototypeWrapper<in T>(prototype: RendererPrototype<T>) : Renderer<T> {

    private val helper = RendererPrototypeHelper(prototype)

    override fun render(context: RenderContext, models: Iterable<T>) {
        this.helper.render(context, models)
    }

    final override fun delete() {
        this.helper.delete()
    }
}