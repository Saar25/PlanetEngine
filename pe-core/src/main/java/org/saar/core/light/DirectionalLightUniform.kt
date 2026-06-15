package org.saar.core.light

import org.saar.lwjgl.opengl.shader.uniforms.UniformContainer
import org.saar.lwjgl.opengl.shader.uniforms.Vec3UniformValue

class DirectionalLightUniform(name: String) : UniformContainer {

    val directionUniform = Vec3UniformValue("$name.direction")

    val colourUniform = Vec3UniformValue("$name.colour")

    override val subUniforms = listOf(this.directionUniform, this.colourUniform)
}