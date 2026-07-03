package org.saar.core.fog

import org.saar.lwjgl.opengl.shader.uniforms.FloatUniformValue
import org.saar.lwjgl.opengl.shader.uniforms.UniformContainer
import org.saar.lwjgl.opengl.shader.uniforms.Vec3UniformValue

class FogUniformValue(name: String) : UniformContainer {

    val colorUniform = Vec3UniformValue("$name.color")

    val startUniform = FloatUniformValue("$name.start")

    val endUniform = FloatUniformValue("$name.end")

    override val subUniforms = listOf(this.colorUniform, this.startUniform, this.endUniform)
}