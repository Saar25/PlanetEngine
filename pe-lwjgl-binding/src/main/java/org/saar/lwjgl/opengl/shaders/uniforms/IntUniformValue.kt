package org.saar.lwjgl.opengl.shaders.uniforms

class IntUniformValue(
    override val name: String,
    override var value: Int = 0
) : IntUniform()