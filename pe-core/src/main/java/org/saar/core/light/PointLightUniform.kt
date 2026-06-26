package org.saar.core.light

import org.saar.lwjgl.opengl.shader.uniforms.UniformContainer
import org.saar.lwjgl.opengl.shader.uniforms.Vec3UniformValue

class PointLightUniform(name: String) : UniformContainer {

    val positionUniform = Vec3UniformValue("$name.position")

    val attenuationUniform = Vec3UniformValue("$name.attenuation")

    val colorUniform = Vec3UniformValue("$name.color")

    override val subUniforms = listOf(this.positionUniform, this.attenuationUniform, this.colorUniform)
}