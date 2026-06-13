package org.saar.core.renderer

import org.saar.lwjgl.opengl.shader.uniforms.Uniform

class RendererPrototypeHelper<T>(private val prototype: RendererPrototype<T>) : Renderer {

    private val uniforms: Map<Uniform, Int> = this.prototype.shadersProgram.bind()
        .let {
            (this.prototype.uniforms + Renderers.findUniforms(this.prototype))
                .flatMap { it.subUniforms }
                .associateWith { this.prototype.shadersProgram.getUniformLocation(it.name) }
        }

    init {
        this.prototype.shadersProgram.bind()
        this.prototype.shadersProgram.bindAttributes(*this.prototype.vertexAttributes())
        this.prototype.shadersProgram.bindFragmentOutputs(*this.prototype.fragmentOutputs())
    }

    fun bind(context: RenderContext) {
        this.prototype.shadersProgram.bind()
        this.prototype.onRenderCycle(context)
    }

    fun unbind() {
        this.prototype.shadersProgram.unbind()
    }

    inline fun doRender(context: RenderContext, renderCallback: () -> Unit) {
        bind(context)
        renderCallback()
        unbind()
    }

    fun render(context: RenderContext, models: Iterable<T>) = doRender(context) {
        models.forEach { render(context, it) }
    }

    fun render(context: RenderContext, model: T) {
        this.prototype.onInstanceDraw(context, model)

        this.uniforms.entries.forEach { (uniform, location) -> uniform.load(location) }

        this.prototype.doInstanceDraw(context, model)
    }

    override fun delete() {
        this.prototype.shadersProgram.delete()
    }
}

fun RendererPrototypeHelper<Unit>.render(context: RenderContext, renderCallback: () -> Unit) {
    bind(context)
    renderCallback()
    render(context, Unit)
    unbind()
}

fun RendererPrototypeHelper<Unit>.render(context: RenderContext) = render(context) {
    render(context, Unit)
}