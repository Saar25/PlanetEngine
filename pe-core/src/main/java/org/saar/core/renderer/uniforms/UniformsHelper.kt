package org.saar.core.renderer.uniforms

import org.saar.lwjgl.opengl.shader.uniforms.UniformWrapper

interface UniformsHelper {
    fun addUniform(uniform: UniformWrapper): UniformsHelper

    fun load()

    private object Empty : UniformsHelper {
        override fun addUniform(uniform: UniformWrapper) = Generic().addUniform(uniform)

        override fun load() = Unit
    }

    private class Generic : UniformsHelper {
        private val uniforms = mutableListOf<UniformWrapper>()

        override fun addUniform(uniform: UniformWrapper) = this.also { this.uniforms.add(uniform) }

        override fun load() = this.uniforms.forEach(UniformWrapper::load)
    }

    companion object {
        fun empty(): UniformsHelper = Empty
    }
}
