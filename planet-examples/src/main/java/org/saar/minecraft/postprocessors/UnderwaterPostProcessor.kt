package org.saar.minecraft.postprocessors

import org.saar.core.postprocessing.PostProcessingBuffers
import org.saar.core.postprocessing.PostProcessor
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.renderpass.RenderPassPrototype
import org.saar.core.renderer.renderpass.RenderPassPrototypeWrapper
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.minecraft.Blocks
import org.saar.minecraft.World

class UnderwaterPostProcessor(private val world: World) : PostProcessor {

    private val prototype = UnderwaterPostProcessorPrototype()
    private val wrapper = RenderPassPrototypeWrapper(prototype)

    override fun render(context: RenderContext, buffers: PostProcessingBuffers) {
        if (world.getBlock(context.camera.transform.position) === Blocks.WATER)
            this.wrapper.render { this.prototype.textureUniform.value = buffers.albedo }
    }

    override fun delete() = this.wrapper.delete()
}

private class UnderwaterPostProcessorPrototype : RenderPassPrototype {

    @UniformProperty
    val textureUniform = TextureUniformValue("u_texture", 0)

    override val fragmentShader: Shader = Shader.createFragment(GlslVersion.V400,
        ShaderCode.loadSource("/minecraft/shaders/underwater.pass.glsl")
    )
}