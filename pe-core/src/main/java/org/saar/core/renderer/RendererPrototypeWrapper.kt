package org.saar.core.renderer

import org.saar.core.mesh.Model
import org.saar.core.renderer.shaders.ShadersHelper
import org.saar.core.renderer.uniforms.UniformTrigger
import org.saar.core.renderer.uniforms.UniformsHelper
import org.saar.core.renderer.uniforms.UpdatersHelper
import org.saar.lwjgl.opengl.shaders.ShadersProgram

open class RendererPrototypeWrapper<T : Model>(
    private val prototype: RendererPrototype<T>,
    private vararg val models: T) : Renderer {

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

    override fun render(context: RenderContext) {
        this.shadersProgram.bind()

        this.prototype.onRenderCycle(context)

        this.uniformsHelper.loadPerRenderCycle()

        for (model in this.models) {
            val state = RenderState(model)

            this.prototype.onInstanceDraw(context, state)

            this.updatersHelper.update(state)
            this.uniformsHelper.loadPerInstance()

            model.draw()
        }

        this.shadersProgram.unbind()
    }

    override fun delete() {
        this.models.forEach { it.delete() }
    }
}