package org.saar.core.renderer

import org.saar.core.renderer.uniforms.UniformsHelper
import org.saar.lwjgl.opengl.shader.ShadersProgram
import org.saar.lwjgl.opengl.shader.uniforms.UniformWrapper

class RendererPrototypeHelper<T>(private val prototype: RendererPrototype<T>) : Renderer {

    private val uniformsHelper: UniformsHelper = UniformsHelper.empty()
        .also { this.prototype.shadersProgram.bind() }
        .let { helper ->
            Renderers.findUniforms(this.prototype)
                .flatMap { it.subUniforms }
                .map { UniformWrapper(this.prototype.shadersProgram.getUniformLocation(it.name), it) }
                .fold(helper) { helper, uniform -> helper.addUniform(uniform) }
        }
        .let { helper ->
            this.prototype.uniforms
                .flatMap { it.subUniforms }
                .map { UniformWrapper(this.prototype.shadersProgram.getUniformLocation(it.name), it) }
                .fold(helper) { helper, uniform -> helper.addUniform(uniform) }
        }

    init {
        this.prototype.shadersProgram.bind()
        this.prototype.shadersProgram.bindAttributes(*this.prototype.vertexAttributes())
        this.prototype.shadersProgram.bindFragmentOutputs(*this.prototype.fragmentOutputs())
    }

    fun beforeRender(context: RenderContext) {
        this.prototype.shadersProgram.bind()
        this.prototype.onRenderCycle(context)
    }

    fun afterRender() {
        this.prototype.shadersProgram.unbind()
    }

    inline fun doRender(context: RenderContext, renderCallback: () -> Unit) {
        beforeRender(context)
        renderCallback()
        afterRender()
    }

    fun render(context: RenderContext, models: Iterable<T>) = doRender(context) {
        models.forEach { render(context, it) }
    }

    fun render(context: RenderContext, model: T) {
        this.prototype.onInstanceDraw(context, model)

        this.uniformsHelper.load()

        this.prototype.doInstanceDraw(context, model)
    }

    override fun delete() {
        this.prototype.shadersProgram.delete()
    }
}

fun RendererPrototypeHelper<Unit>.render(context: RenderContext, renderCallback: () -> Unit) {
    beforeRender(context)
    renderCallback()
    render(context, Unit)
    afterRender()
}

fun RendererPrototypeHelper<Unit>.render(context: RenderContext) = render(context) {
    render(context, Unit)
}