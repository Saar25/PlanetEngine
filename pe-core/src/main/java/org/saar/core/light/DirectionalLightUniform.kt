package org.saar.core.light

import org.saar.lwjgl.opengl.shaders.ShadersProgram
import org.saar.lwjgl.opengl.shaders.uniforms2.Uniform
import org.saar.lwjgl.opengl.shaders.uniforms2.Vec3UniformValue

abstract class DirectionalLightUniform(val name: String) : Uniform {

    private val directionUniform = Vec3UniformValue("$name.direction")

    private val colourUniform = Vec3UniformValue("$name.colour")

    final override fun initialize(shadersProgram: ShadersProgram) {
        this.directionUniform.initialize(shadersProgram)
        this.colourUniform.initialize(shadersProgram)
    }

    final override fun load() {
        val value = getUniformValue()
        this.colourUniform.value = value.colour
        this.directionUniform.value = value.direction.normalize()

        this.colourUniform.load()
        this.directionUniform.load()
    }

    abstract fun getUniformValue(): DirectionalLight
}