package org.saar.core.renderer.deferred

import org.saar.core.renderer.Renderers
import org.saar.core.renderer.uniforms.UniformTrigger
import org.saar.core.renderer.uniforms.UniformsHelper
import org.saar.lwjgl.opengl.constants.RenderMode
import org.saar.lwjgl.opengl.objects.vaos.Vao
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.ShadersProgram
import org.saar.lwjgl.opengl.utils.GlRendering

open class RenderPassPrototypeWrapper(private val prototype: RenderPassPrototype) : RenderPass {

    companion object {
        private val vertexShaderCode = ShaderCode.loadSource(
            "/shaders/postprocessing/default.vertex.glsl")
    }

    private val shadersProgram: ShadersProgram = ShadersProgram.create(
        Shader.createVertex(GlslVersion.V400, vertexShaderCode),
        this.prototype.fragmentShader())

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

    override fun render(context: RenderPassContext) {
        this.shadersProgram.bind()

        doRender(context)

        this.shadersProgram.unbind()
    }

    protected open fun doRender(context: RenderPassContext) {
        this.prototype.onRender(context)
        drawQuad()
    }

    protected fun drawQuad() {
        this.uniformsHelper.load()

        Vao.EMPTY.bind()
        GlRendering.drawArrays(RenderMode.TRIANGLE_STRIP, 0, 4)
    }

    override fun delete() {
        this.shadersProgram.delete()
    }
}