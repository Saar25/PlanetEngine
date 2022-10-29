package org.saar.lwjgl.opengl.shader.uniforms

class FloatUniformValue(
    override val name: String,
    override var value: Float = 0f
) : FloatUniform()