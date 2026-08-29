package org.saar.core.renderer

import org.saar.lwjgl.opengl.shader.uniforms.Uniform

class ShadersUniformsLoader(private val uniforms: Map<Uniform, Int>) {

    fun load() = this.uniforms.entries.forEach { (uniform, location) -> uniform.load(location) }

    companion object {
        fun from(shadersLink: ShadersLink): ShadersUniformsLoader {
            shadersLink.shadersProgram.bind()

            val uniforms = (shadersLink.uniforms + Renderers.findUniforms(shadersLink))
                .flatMap { it.subUniforms }
                .associateWith { shadersLink.shadersProgram.getUniformLocation(it.name) }

            return ShadersUniformsLoader(uniforms)
        }
    }
}