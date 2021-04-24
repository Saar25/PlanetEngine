package org.saar.core.postprocessing

import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.Renderers
import org.saar.core.renderer.uniforms.UniformTrigger
import org.saar.core.renderer.uniforms.UniformsHelper
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.ShadersProgram
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture

open class PostProcessorPrototypeWrapper(private val prototype: PostProcessorPrototype) : PostProcessor {

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

    override fun process(image: ReadOnlyTexture) {
        this.shadersProgram.bind()

        doProcess(PostProcessingContext(image))

        this.shadersProgram.unbind()
    }

    protected open fun doProcess(context: PostProcessingContext) {
        this.prototype.onRender(context)
        drawQuad()
    }

    protected fun drawQuad() {
        this.uniformsHelper.load()

        QuadMesh.draw()
    }

    override fun delete() {
        this.shadersProgram.delete()
        this.prototype.onDelete()
    }
}