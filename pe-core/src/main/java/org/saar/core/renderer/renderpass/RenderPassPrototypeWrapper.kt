package org.saar.core.renderer.renderpass

import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.Renderers
import org.saar.core.renderer.uniforms.UniformTrigger
import org.saar.core.renderer.uniforms.UniformsHelper
import org.saar.lwjgl.opengl.shader.ShadersProgram
import org.saar.lwjgl.opengl.shader.uniforms.UniformWrapper

class RenderPassPrototypeWrapper(private val prototype: RenderPassPrototype) {

    private val shadersProgram: ShadersProgram = ShadersProgram.create(
        this.prototype.vertexShader,
        this.prototype.fragmentShader
    )

    private val uniformsHelper: UniformsHelper = UniformsHelper.empty()
        .also { this.shadersProgram.bind() }
        .let {
            Renderers.findUniformsByTrigger(this.prototype, UniformTrigger.ALWAYS)
                .flatMap { u -> u.subUniforms }
                .map { u -> UniformWrapper(this.shadersProgram.getUniformLocation(u.name), u) }
                .fold(it) { helper, uniform -> helper.addUniform(uniform) }
        }
        .let {
            Renderers.findUniformsByTrigger(this.prototype, UniformTrigger.PER_INSTANCE)
                .flatMap { u -> u.subUniforms }
                .map { u -> UniformWrapper(this.shadersProgram.getUniformLocation(u.name), u) }
                .fold(it) { helper, uniform -> helper.addPerInstanceUniform(uniform) }
        }
        .let {
            Renderers.findUniformsByTrigger(this.prototype, UniformTrigger.PER_RENDER_CYCLE)
                .flatMap { u -> u.subUniforms }
                .map { u -> UniformWrapper(this.shadersProgram.getUniformLocation(u.name), u) }
                .fold(it) { helper, uniform -> helper.addPerRenderCycleUniform(uniform) }
        }

    init {
        this.shadersProgram.bind()
        this.shadersProgram.bindFragmentOutputs("f_colour")
    }

    fun beforeRender() {
        this.shadersProgram.bind()
    }

    fun afterRender() {
        this.shadersProgram.unbind()
    }

    inline fun render(beforeDraw: () -> Unit = {}) {
        beforeRender()

        beforeDraw()
        drawQuad()

        afterRender()
    }

    fun drawQuad() {
        this.uniformsHelper.load()

        QuadMesh.draw()
    }

    fun delete() {
        this.shadersProgram.delete()
    }
}