package org.saar.core.renderer.renderpass

import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.Renderers
import org.saar.core.renderer.uniforms.UniformTrigger
import org.saar.core.renderer.uniforms.UniformsHelper
import org.saar.lwjgl.opengl.shaders.ShadersProgram

class RenderPassPrototypeWrapper(private val prototype: RenderPassPrototype) : RenderPass {

    private val shadersProgram: ShadersProgram = ShadersProgram.create(
        this.prototype.vertexShader(),
        this.prototype.fragmentShader()
    )

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
        this.shadersProgram.bindFragmentOutputs("f_colour")
    }

    fun beforeRender() {
        this.shadersProgram.bind()
    }

    fun afterRender() {
        this.shadersProgram.unbind()
    }

    inline fun render(beforeDraw: () -> Unit) {
        beforeRender()

        beforeDraw()
        drawQuad()

        afterRender()
    }

    fun drawQuad() {
        this.uniformsHelper.load()

        QuadMesh.draw()
    }

    override fun delete() {
        this.shadersProgram.delete()
    }
}