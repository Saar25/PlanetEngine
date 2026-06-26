package org.saar.core.light

import org.saar.lwjgl.opengl.shader.uniforms.UniformContainer
import org.saar.lwjgl.opengl.shader.uniforms.Vec3UniformValue

class DirectionalLightUniform(name: String) : UniformContainer {

    val directionUniform = Vec3UniformValue("$name.direction")

    val colorUniform = Vec3UniformValue("$name.color")

    override val subUniforms = listOf(this.directionUniform, this.colorUniform)
}