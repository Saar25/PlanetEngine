package org.saar.core.fog

import org.saar.lwjgl.opengl.shaders.ShadersProgram
import org.saar.lwjgl.opengl.shaders.uniforms.FloatUniformValue
import org.saar.lwjgl.opengl.shaders.uniforms.Uniform
import org.saar.lwjgl.opengl.shaders.uniforms.Vec3UniformValue

abstract class FogUniformValue : Uniform {

    private val colourUniform = Vec3UniformValue("${getName()}.colour")

    private val startUniform = FloatUniformValue("${getName()}.start")

    private val endUniform = FloatUniformValue("${getName()}.end")

    final override fun initialize(shadersProgram: ShadersProgram) {
        this.colourUniform.initialize(shadersProgram)
        this.startUniform.initialize(shadersProgram)
        this.endUniform.initialize(shadersProgram)
    }

    final override fun load() {
        val value = getUniformValue()

        this.colourUniform.value = value.colour
        this.colourUniform.load()

        this.startUniform.value = value.start
        this.startUniform.load()

        this.endUniform.value = value.end
        this.endUniform.load()
    }

    abstract fun getName(): String

    abstract fun getUniformValue(): IFog
}