package org.saar.core.renderer

import org.saar.core.renderer.uniforms.UniformsHelper
import org.saar.lwjgl.opengl.shader.ShadersProgram
import org.saar.lwjgl.opengl.shader.uniforms.UniformWrapper

class RendererPrototypeHelper<T>(private val prototype: RendererPrototype<T>) : Renderer {

    private val shadersProgram: ShadersProgram = ShadersProgram.create(*this.prototype.shaders)

    private val uniformsHelper: UniformsHelper = UniformsHelper.empty()
        .also { this.shadersProgram.bind() }
        .let { helper ->
            Renderers.findUniforms(this.prototype)
                .flatMap { it.subUniforms }
                .map { UniformWrapper(this.shadersProgram.getUniformLocation(it.name), it) }
                .fold(helper) { helper, uniform -> helper.addUniform(uniform) }
        }
        .let { helper ->
            this.prototype.uniforms
                .flatMap { it.subUniforms }
                .map { UniformWrapper(this.shadersProgram.getUniformLocation(it.name), it) }
                .fold(helper) { helper, uniform -> helper.addUniform(uniform) }
        }

    init {
        this.shadersProgram.bind()
        this.shadersProgram.bindAttributes(*this.prototype.vertexAttributes())
        this.shadersProgram.bindFragmentOutputs(*this.prototype.fragmentOutputs())
    }

    fun beforeRender(context: RenderContext) {
        this.shadersProgram.bind()
        this.prototype.onRenderCycle(context)
    }

    fun afterRender() {
        this.shadersProgram.unbind()
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
        this.shadersProgram.delete()
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