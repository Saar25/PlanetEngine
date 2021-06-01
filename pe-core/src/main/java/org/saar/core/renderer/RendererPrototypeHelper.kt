package org.saar.core.renderer

import org.saar.core.renderer.shaders.ShadersHelper
import org.saar.core.renderer.uniforms.UniformTrigger
import org.saar.core.renderer.uniforms.UniformsHelper
import org.saar.lwjgl.opengl.shaders.ShadersProgram

class RendererPrototypeHelper<T>(private val prototype: RendererPrototype<T>) : Renderer {

    private val shadersProgram: ShadersProgram = ShadersHelper.empty()
        .let {
            Renderers.findVertexShaders(this.prototype)
                .fold(it) { helper, shader -> helper.addShader(shader) }
        }
        .let {
            Renderers.findFragmentShaders(this.prototype)
                .fold(it) { helper, shader -> helper.addShader(shader) }
        }
        .createProgram()

    private val uniformsHelper: UniformsHelper = UniformsHelper.empty()
        .let {
            Renderers.findUniformsByTrigger(this.prototype, UniformTrigger.ALWAYS)
                .fold(it) { helper, uniform -> helper.addUniform(uniform) }
        }
        .let {
            Renderers.findUniformsByTrigger(this.prototype, UniformTrigger.PER_INSTANCE)
                .fold(it) { helper, uniform -> helper.addPerInstanceUniform(uniform) }
        }
        .let {
            Renderers.findUniformsByTrigger(this.prototype, UniformTrigger.PER_RENDER_CYCLE)
                .fold(it) { helper, uniform -> helper.addPerRenderCycleUniform(uniform) }
        }

    init {
        this.shadersProgram.bind()
        this.uniformsHelper.initialize(this.shadersProgram)
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