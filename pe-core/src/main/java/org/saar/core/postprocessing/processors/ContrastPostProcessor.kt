package org.saar.core.postprocessing.processors

import org.saar.core.postprocessing.PostProcessingContext
import org.saar.core.postprocessing.PostProcessor
import org.saar.core.postprocessing.PostProcessorPrototype
import org.saar.core.postprocessing.PostProcessorPrototypeWrapper
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.uniforms.FloatUniform
import org.saar.lwjgl.opengl.shaders.uniforms.TextureUniformValue

class ContrastPostProcessor(contrast: Float) : PostProcessor,
    PostProcessorPrototypeWrapper(ContrastPostProcessorPrototype(contrast)) {

}

private class ContrastPostProcessorPrototype(private val contrast: Float) : PostProcessorPrototype {

    @UniformProperty
    private val textureUniform = TextureUniformValue("u_texture", 0)

    @UniformProperty
    private val contrastUniform = object : FloatUniform() {
        override fun getName() = "u_contrast"

        override fun getUniformValue() = this@ContrastPostProcessorPrototype.contrast
    }

    override fun fragmentShader(): Shader = Shader.createFragment(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/postprocessing/contrast.pass.glsl"))

    override fun onRender(context: PostProcessingContext) {
        this.textureUniform.value = context.texture
    }

    override fun onDelete() {
    }
}