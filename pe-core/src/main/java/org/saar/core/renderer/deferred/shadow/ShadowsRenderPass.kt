package org.saar.core.renderer.deferred.shadow

import org.joml.Matrix4f
import org.saar.core.camera.ICamera
import org.saar.core.renderer.AUniformProperty
import org.saar.core.renderer.UniformsHelper
import org.saar.core.renderer.deferred.DeferredRenderingBuffers
import org.saar.core.renderer.deferred.RenderPass
import org.saar.core.renderer.deferred.RenderPassBase
import org.saar.lwjgl.opengl.constants.RenderMode
import org.saar.lwjgl.opengl.objects.vaos.Vao
import org.saar.lwjgl.opengl.shaders.InstanceRenderState
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShadersProgram
import org.saar.lwjgl.opengl.shaders.StageRenderState
import org.saar.lwjgl.opengl.shaders.uniforms.UniformTextureProperty
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture
import org.saar.lwjgl.opengl.utils.GlRendering
import org.saar.maths.utils.Matrix4

class ShadowsRenderPass(private val camera: ICamera, private val shadowMap: ReadOnlyTexture)
    : RenderPassBase(shadersProgram), RenderPass {

    private var uniformsHelper: UniformsHelper<DeferredRenderingBuffers> = UniformsHelper.empty()

    @AUniformProperty
    private val shadowMapUniform = object : UniformTextureProperty.Stage("shadowMap", 0) {
        override fun getUniformValue(state: StageRenderState): ReadOnlyTexture {
            return this@ShadowsRenderPass.shadowMap
        }
    }

    @AUniformProperty
    private val depthTextureUniform = object : UniformTextureProperty.Instance<DeferredRenderingBuffers>("depthTexture", 1) {
        override fun getUniformValue(state: InstanceRenderState<DeferredRenderingBuffers>): ReadOnlyTexture {
            return state.instance.depth
        }
    }

    init {
        shadersProgram.bindFragmentOutputs("f_colour")

        this.uniformsHelper = buildHelper(uniformsHelper)
    }

    companion object {
        private val matrix: Matrix4f = Matrix4.create()

        private val vertex: Shader = Shader.createVertex(
                "/shaders/deferred/quadVertex.glsl")
        private val fragment: Shader = Shader.createFragment(
                "/shaders/deferred/shadow/fragment.glsl")
        private val shadersProgram: ShadersProgram =
                ShadersProgram.create(vertex, fragment)
    }


    override fun onRender(buffers: DeferredRenderingBuffers) {
        val stage = StageRenderState()
        this.uniformsHelper.loadOnStage(stage)

        val instance = InstanceRenderState(buffers)
        this.uniformsHelper.loadOnInstance(instance)

        Vao.EMPTY.bind()
        GlRendering.drawArrays(RenderMode.TRIANGLE_STRIP, 0, 4)
    }
}