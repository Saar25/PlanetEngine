package org.saar.core.light

import org.saar.lwjgl.opengl.shaders.uniforms.UniformContainer
import org.saar.lwjgl.opengl.shaders.uniforms.Vec3Uniform

class DirectionalLightUniformValue(name: String, var value: IDirectionalLight) : UniformContainer {

    private val directionUniform = object : Vec3Uniform() {
        override val name = "$name.direction"

        override val value get() = this@DirectionalLightUniformValue.value.direction
    }

    private val colourUniform = object : Vec3Uniform() {
        override val name = "$name.colour"

        override val value get() = this@DirectionalLightUniformValue.value.colour
    }

    override val subUniforms = listOf(this.directionUniform, this.colourUniform)
}