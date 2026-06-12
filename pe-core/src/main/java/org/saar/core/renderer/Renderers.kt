package org.saar.core.renderer

import org.saar.core.renderer.uniforms.UniformPropertiesLocator
import org.saar.lwjgl.opengl.shader.uniforms.UniformContainer

object Renderers {

    fun findUniforms(renderer: Any): Collection<UniformContainer> {
        return UniformPropertiesLocator(renderer).findUniform()
    }
}
