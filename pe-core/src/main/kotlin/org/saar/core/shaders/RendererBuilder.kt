package org.saar.core.shaders

import org.saar.core.renderer.*
import org.saar.core.shaders.RendererBuilder.DummyRenderer

typealias DoRender<C, T> = DummyRenderer<C, T>.(C, Iterable<T>) -> Unit

class RendererBuilder<C : RenderContext, T> {

    lateinit var shadersLink: ShadersLink
    lateinit var doRender: DoRender<C, T>

    fun build(): Renderer<C, T> = DummyRenderer(this.shadersLink, this.doRender)

    class DummyRenderer<C : RenderContext, T>(
        private val shadersLink: ShadersLink,
        private val doRender: DummyRenderer<C, T>.(C, Iterable<T>) -> Unit
    ) : Renderer<C, T> {
        val uniformsLoader = ShadersUniformsLoader.from(this.shadersLink)

        init {
            this.shadersLink.init()
        }

        override fun render(context: C, models: Iterable<T>) {
            this.shadersLink.shadersProgram.bind()
            this.doRender(context, models)
        }

        override fun delete() = this.shadersLink.shadersProgram.delete()
    }
}