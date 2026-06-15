package org.saar.core.fog

import org.saar.lwjgl.opengl.shader.uniforms.FloatUniformValue
import org.saar.lwjgl.opengl.shader.uniforms.UniformContainer
import org.saar.lwjgl.opengl.shader.uniforms.Vec3UniformValue

class FogUniformValue(name: String) : UniformContainer {

    val colourUniform = Vec3UniformValue("$name.colour")

    val startUniform = FloatUniformValue("$name.start")

    val endUniform = FloatUniformValue("$name.end")

    override val subUniforms = listOf(this.colourUniform, this.startUniform, this.endUniform)
}