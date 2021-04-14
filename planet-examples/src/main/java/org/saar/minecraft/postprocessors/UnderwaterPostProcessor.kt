package org.saar.minecraft.postprocessors

import org.saar.core.postprocessing.PostProcessingContext
import org.saar.core.postprocessing.PostProcessor
import org.saar.core.postprocessing.PostProcessorPrototype
import org.saar.core.postprocessing.PostProcessorPrototypeWrapper
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.uniforms.TextureUniformValue

class UnderwaterPostProcessor : PostProcessor,
    PostProcessorPrototypeWrapper(UnderwaterPostProcessorPrototype())

private class UnderwaterPostProcessorPrototype : PostProcessorPrototype {

    @UniformProperty
    private val textureUniform = TextureUniformValue("u_texture", 0)

    override fun fragmentShader(): Shader = Shader.createFragment(GlslVersion.V400,
        ShaderCode.loadSource("/minecraft/shaders/underwater.pass.glsl"))

    override fun onRender(context: PostProcessingContext) {
        this.textureUniform.value = context.texture
    }

    override fun onDelete() {
    }
}