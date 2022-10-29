package org.saar.lwjgl.opengl.shader.uniforms

class BooleanUniformValue(
    override val name: String,
    override var value: Boolean = false
) : BooleanUniform()