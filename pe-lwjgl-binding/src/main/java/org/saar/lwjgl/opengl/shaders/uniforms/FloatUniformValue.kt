package org.saar.lwjgl.opengl.shaders.uniforms

class FloatUniformValue(
    override val name: String,
    override var value: Float = 0f
) : FloatUniform()