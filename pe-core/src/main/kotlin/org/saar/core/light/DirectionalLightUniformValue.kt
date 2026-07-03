package org.saar.core.light

import org.saar.lwjgl.opengl.shader.uniforms.UniformContainer
import org.saar.lwjgl.opengl.shader.uniforms.Vec3Uniform

class DirectionalLightUniformValue(name: String, var value: IDirectionalLight) : UniformContainer {

    private val directionUniform = object : Vec3Uniform() {
        override val name = "$name.direction"

        override val value get() = this@DirectionalLightUniformValue.value.direction
    }

    private val colorUniform = object : Vec3Uniform() {
        override val name = "$name.color"

        override val value get() = this@DirectionalLightUniformValue.value.color
    }

    override val subUniforms = listOf(this.directionUniform, this.colorUniform)
}