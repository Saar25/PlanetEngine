package org.saar.core.renderer

import org.saar.core.mesh.Model
import org.saar.core.renderer.shaders.ShadersHelper
import org.saar.core.renderer.uniforms.UniformTrigger
import org.saar.core.renderer.uniforms.UniformsHelper
import org.saar.core.renderer.uniforms.UpdatersHelper
import org.saar.lwjgl.opengl.shaders.ShadersProgram

abstract class RendererPrototypeWrapper<T : Model>(private val prototype: RendererPrototype<T>) : Renderer {

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

    private val updatersHelper: UpdatersHelper<T> = UpdatersHelper.empty<T>()
        .let {
            Renderers.findUniformsUpdaters<T>(this.prototype)
                .fold(it) { helper, updater -> helper.addUpdater(updater) }
        }

    init {
        this.shadersProgram.bind()
        this.uniformsHelper.initialize(this.shadersProgram)
        this.shadersProgram.bindAttributes(*this.prototype.vertexAttributes())
        this.shadersProgram.bindFragmentOutputs(*this.prototype.fragmentOutputs())
    }

    protected open fun render(context: RenderContext, vararg models: T) {
        this.shadersProgram.bind()

        this.prototype.onRenderCycle(context)

        this.uniformsHelper.loadPerRenderCycle()

        doRender(context, models)

        this.shadersProgram.unbind()
    }

    protected open fun doRender(context: RenderContext, models: Array<out T>) {
        renderModels(context, models)
    }

    protected open fun renderModels(context: RenderContext, models: Array<out T>) {
        models.forEach { renderModel(context, it) }
    }

    protected open fun renderModels(context: RenderContext, models: Iterable<T>) {
        models.forEach { renderModel(context, it) }
    }

    protected open fun renderModel(context: RenderContext, model: T) {
        this.prototype.onInstanceDraw(context, model)

        this.updatersHelper.update(model)
        this.uniformsHelper.loadPerInstance()

        doRenderModel(context, model)
    }

    protected open fun doRenderModel(context: RenderContext, model: T) {
        model.draw()
    }
}