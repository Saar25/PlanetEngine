package org.saar.core.light

import org.saar.lwjgl.opengl.shaders.ShadersProgram
import org.saar.lwjgl.opengl.shaders.uniforms.Uniform
import org.saar.lwjgl.opengl.shaders.uniforms.Vec3UniformValue

abstract class PointLightUniform(val name: String) : Uniform {

    private val positionUniform = Vec3UniformValue("$name.position")
    private val attenuationUniform = Vec3UniformValue("$name.attenuation")
    private val colourUniform = Vec3UniformValue("$name.colour")

    final override fun initialize(shadersProgram: ShadersProgram) {
        this.positionUniform.initialize(shadersProgram)
        this.attenuationUniform.initialize(shadersProgram)
        this.colourUniform.initialize(shadersProgram)
    }

    final override fun load() {
        val value = getUniformValue()
        this.positionUniform.value = value.position
        this.attenuationUniform.value = value.attenuation.vector3f
        this.colourUniform.value = value.colour

        this.positionUniform.load()
        this.attenuationUniform.load()
        this.colourUniform.load()
    }

    abstract fun getUniformValue(): IPointLight
}