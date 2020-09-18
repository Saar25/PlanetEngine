package org.saar.core.renderer.deferred.light

import org.saar.core.light.DirectionalLight
import org.saar.core.light.DirectionalLightUniformProperty
import org.saar.core.renderer.AUniformProperty
import org.saar.core.renderer.deferred.RenderPass
import org.saar.core.renderer.deferred.RenderPassBase
import org.saar.lwjgl.opengl.constants.RenderMode
import org.saar.lwjgl.opengl.objects.vaos.Vao
import org.saar.lwjgl.opengl.shaders.InstanceRenderState
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShadersProgram
import org.saar.lwjgl.opengl.shaders.StageRenderState
import org.saar.lwjgl.opengl.shaders.uniforms.UniformArrayProperty
import org.saar.lwjgl.opengl.shaders.uniforms.UniformTextureProperty
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture
import org.saar.lwjgl.opengl.utils.GlRendering
import org.saar.lwjgl.opengl.utils.GlUtils

class LightRenderPass(private val input: LightRenderPassInput) : RenderPassBase(shadersProgram), RenderPass {

    @AUniformProperty
    private val colourTextureUniform = object : UniformTextureProperty<ReadOnlyTexture>("colourTexture", 0) {
        override fun getInstanceValue(state: InstanceRenderState<ReadOnlyTexture>): ReadOnlyTexture {
            return state.instance
        }
    }

    @AUniformProperty
    private val normalTextureUniform = object : UniformTextureProperty<ReadOnlyTexture>("normalTexture", 1) {
        override fun getStageValue(state: StageRenderState): ReadOnlyTexture {
            return this@LightRenderPass.input.normalTexture
        }
    }

    @AUniformProperty
    private val depthTextureUniform = object : UniformTextureProperty<ReadOnlyTexture>("depthTexture", 2) {
        override fun getStageValue(state: StageRenderState): ReadOnlyTexture {
            return this@LightRenderPass.input.depthTexture
        }
    }

    @AUniformProperty
    private val directionalLightsUniform = UniformArrayProperty<DirectionalLight>("directionalLights", 5)
    { name, index -> DirectionalLightUniformProperty(name) }

    companion object {
        private val vertex: Shader = Shader.createVertex(
                "/shaders/deferred/quadVertex.glsl")
        private val fragment: Shader = Shader.createFragment(
                "/shaders/deferred/light/fragment.glsl")
        private val shadersProgram: ShadersProgram =
                ShadersProgram.create(vertex, fragment)
    }

    init {
        shadersProgram.bindFragmentOutputs("f_colour")
        init()
    }

    override fun onRender(image: ReadOnlyTexture) {
        GlUtils.disableCulling()

        val stageState = StageRenderState()
        this.normalTextureUniform.loadOnStage(stageState)
        this.depthTextureUniform.loadOnStage(stageState)

        val instanceState = InstanceRenderState(image)
        this.colourTextureUniform.loadOnInstance(instanceState)

        val light = DirectionalLight()
        light.colour.set(1.0f, 1.0f, 1.0f)
        light.direction.set(-50f ,-50f ,-50f)
        val state = InstanceRenderState<DirectionalLight>(light)
        this.directionalLightsUniform.loadOnInstance(state)

        // TODO: bind some default vao, cannot draw without bound vao!
        Vao.EMPTY.bind()
        GlRendering.drawArrays(RenderMode.TRIANGLE_STRIP, 0, 4)
    }
}