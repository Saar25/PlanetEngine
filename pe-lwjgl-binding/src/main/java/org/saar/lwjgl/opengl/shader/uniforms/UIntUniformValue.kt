package org.saar.lwjgl.opengl.shader.uniforms

class UIntUniformValue(
    override val name: String,
    override var value: Int = 0
) : UIntUniform()