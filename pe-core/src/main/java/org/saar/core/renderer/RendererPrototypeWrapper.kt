package org.saar.core.renderer

abstract class RendererPrototypeWrapper<in T>(private val prototype: RendererPrototype<T>) : Renderer<T> {

    private val shadersLink = this.prototype
    private val uniformsLoader = ShadersUniformsLoader.from(this.shadersLink)

    init {
        this.shadersLink.init()
    }

    override fun render(context: RenderContext, models: Iterable<T>) {
        this.shadersLink.shadersProgram.bind()

        this.prototype.onRenderCycle(context)

        models.forEach { model ->
            this.prototype.onInstanceDraw(context, model)

            this.uniformsLoader.load()

            this.prototype.doInstanceDraw(context, model)
        }
    }

    final override fun delete() = this.shadersLink.shadersProgram.delete()
}