package org.saar.core.renderer.deferred.light

import org.saar.core.renderer.AbstractRenderer
import org.saar.core.renderer.annotations.StageUniformProperty
import org.saar.core.renderer.deferred.RenderPass
import org.saar.lwjgl.opengl.constants.RenderMode
import org.saar.lwjgl.opengl.shaders.RenderState
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShadersProgram
import org.saar.lwjgl.opengl.shaders.uniforms.UniformTextureProperty
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture
import org.saar.lwjgl.opengl.utils.GlRendering

class LightRenderPass(private val input: LightRenderPassInput, private val output: LightRenderPassOutput)
    : AbstractRenderer(shadersProgram), RenderPass {

    @StageUniformProperty
    private val colourTextureUniform = object : UniformTextureProperty<Any>("colourTexture", 0) {
        override fun getUniformValue(state: RenderState<Any>?): ReadOnlyTexture {
            return this@LightRenderPass.input.colourTexture
        }
    }

    @StageUniformProperty
    private val normalTextureUniform = object : UniformTextureProperty<Any>("normalTexture", 1) {
        override fun getUniformValue(state: RenderState<Any>?): ReadOnlyTexture {
            return this@LightRenderPass.input.normalTexture
        }
    }

    @StageUniformProperty
    private val depthTextureUniform = object : UniformTextureProperty<Any>("depthTexture", 2) {
        override fun getUniformValue(state: RenderState<Any>?): ReadOnlyTexture {
            return this@LightRenderPass.input.depthTexture
        }
    }

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

    override fun onRender() {
        output.outputImage
        this.colourTextureUniform.load(null)
        this.normalTextureUniform.load(null)
        this.depthTextureUniform.load(null)

        // TODO: bind some default vao, cannot draw without bound vao!
        GlRendering.drawArrays(RenderMode.TRIANGLE_STRIP, 0, 4)
    }
}