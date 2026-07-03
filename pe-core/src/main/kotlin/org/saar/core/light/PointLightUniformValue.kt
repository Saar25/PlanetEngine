package org.saar.core.light

import org.saar.lwjgl.opengl.shader.uniforms.UniformContainer
import org.saar.lwjgl.opengl.shader.uniforms.Vec3Uniform

class PointLightUniformValue(name: String, var value: IPointLight) : UniformContainer {

    private val positionUniform = object : Vec3Uniform() {
        override val name = "$name.position"

        override val value get() = this@PointLightUniformValue.value.position
    }

    private val attenuationUniform = object : Vec3Uniform() {
        override val name = "$name.attenuation"

        override val value get() = this@PointLightUniformValue.value.attenuation.vector3f
    }

    private val colorUniform = object : Vec3Uniform() {
        override val name = "$name.color"

        override val value get() = this@PointLightUniformValue.value.color
    }

    override val subUniforms = listOf(this.positionUniform, this.attenuationUniform, this.colorUniform)
}