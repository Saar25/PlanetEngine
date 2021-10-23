package org.saar.core.fog

import org.saar.lwjgl.opengl.shaders.uniforms.FloatUniform
import org.saar.lwjgl.opengl.shaders.uniforms.UniformContainer
import org.saar.lwjgl.opengl.shaders.uniforms.Vec3Uniform

class FogUniformValue(name: String, var value: IFog) : UniformContainer {

    private val colourUniform = object : Vec3Uniform() {
        override val name = "$name.colour"

        override val value get() = this@FogUniformValue.value.colour
    }

    private val startUniform = object : FloatUniform() {
        override val name = "$name.start"

        override val value get() = this@FogUniformValue.value.start
    }

    private val endUniform = object : FloatUniform() {
        override val name = "$name.end"

        override val value get() = this@FogUniformValue.value.end
    }

    override val subUniforms = listOf(this.colourUniform, this.startUniform, this.endUniform)
}