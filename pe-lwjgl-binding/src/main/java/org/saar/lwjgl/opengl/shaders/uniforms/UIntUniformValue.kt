package org.saar.lwjgl.opengl.shaders.uniforms

class UIntUniformValue(
    override val name: String,
    override var value: Int = 0
) : UIntUniform()