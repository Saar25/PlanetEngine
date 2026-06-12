package org.saar.core.renderer

import org.saar.core.renderer.uniforms.UniformTrigger
import org.saar.core.renderer.uniforms.UniformsHelper
import org.saar.lwjgl.opengl.shader.ShadersProgram
import org.saar.lwjgl.opengl.shader.uniforms.UniformWrapper

class RendererPrototypeHelper<T>(private val prototype: RendererPrototype<T>) : Renderer {

    private val shadersProgram: ShadersProgram = ShadersProgram.create(*this.prototype.shaders)

    private val uniformsHelper: UniformsHelper = UniformsHelper.empty()
        .also { this.shadersProgram.bind() }
        .let { helper ->
            Renderers.findUniformsByTrigger(this.prototype, UniformTrigger.ALWAYS)
                .flatMap { it.subUniforms }
                .map { UniformWrapper(this.shadersProgram.getUniformLocation(it.name), it) }
                .fold(helper) { helper, uniform -> helper.addUniform(uniform) }
        }
        .let { helper ->
            Renderers.findUniformsByTrigger(this.prototype, UniformTrigger.PER_INSTANCE)
                .flatMap { it.subUniforms }
                .map { UniformWrapper(this.shadersProgram.getUniformLocation(it.name), it) }
                .fold(helper) { helper, uniform -> helper.addPerInstanceUniform(uniform) }
        }
        .let { helper ->
            Renderers.findUniformsByTrigger(this.prototype, UniformTrigger.PER_RENDER_CYCLE)
                .flatMap { it.subUniforms }
                .map { UniformWrapper(this.shadersProgram.getUniformLocation(it.name), it) }
                .fold(helper) { helper, uniform -> helper.addPerRenderCycleUniform(uniform) }
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
        this.uniformsHelper.loadPerRenderCycle()
    }

    fun afterRender() {
        this.shadersProgram.unbind()
    }

    inline fun render(context: RenderContext, renderCallback: () -> Unit) {
        beforeRender(context)
        renderCallback()
        afterRender()
    }

    fun render(context: RenderContext, models: Iterable<T>) = render(context) {
        models.forEach { render(context, it) }
    }

    fun render(context: RenderContext, vararg models: T) = render(context) {
        models.forEach { render(context, it) }
    }

    fun render(context: RenderContext, model: T) {
        this.prototype.onInstanceDraw(context, model)

        this.uniformsHelper.loadPerInstance()

        this.prototype.doInstanceDraw(context, model)
    }

    override fun delete() {
        this.shadersProgram.delete()
    }
}