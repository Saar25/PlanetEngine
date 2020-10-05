package org.saar.core.light

import org.saar.lwjgl.opengl.shaders.ShadersProgram
import org.saar.lwjgl.opengl.shaders.uniforms.UniformVec3Property
import org.saar.lwjgl.opengl.shaders.uniforms2.Uniform

abstract class DirectionalLightUniform(val name: String) : Uniform {

    private val directionUniform = UniformVec3Property("$name.direction")

    private val colourUniform = UniformVec3Property("$name.colour")

    final override fun initialize(shadersProgram: ShadersProgram) {
        this.directionUniform.initialize(shadersProgram)
        this.colourUniform.initialize(shadersProgram)
    }

    final override fun load() {
        val value = getUniformValue()
        this.colourUniform.loadValue(value.colour)
        this.directionUniform.loadValue(value.direction.normalize())
    }

    abstract fun getUniformValue(): DirectionalLight
}