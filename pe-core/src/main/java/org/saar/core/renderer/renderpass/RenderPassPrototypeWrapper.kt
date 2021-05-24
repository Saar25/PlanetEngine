package org.saar.core.renderer.renderpass

import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.Renderers
import org.saar.core.renderer.uniforms.UniformTrigger
import org.saar.core.renderer.uniforms.UniformsHelper
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.ShadersProgram

open class RenderPassPrototypeWrapper<T : RenderPassBuffers>(
    private val prototype: RenderPassPrototype<T>) : RenderPass {

    companion object {
        private val vertexShaderCode = ShaderCode.loadSource(
            "/shaders/common/quad/quad.vertex.glsl")
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

    protected open fun render(context: RenderPassContext, buffers: T) {
        this.shadersProgram.bind()

        this.prototype.onRender(context, buffers)
        drawQuad()

        this.shadersProgram.unbind()
    }

    private fun drawQuad() {
        this.uniformsHelper.load()

        QuadMesh.draw()
    }

    override fun delete() {
        this.shadersProgram.delete()
    }
}