package org.saar.core.renderer.renderpass

import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.Renderers
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