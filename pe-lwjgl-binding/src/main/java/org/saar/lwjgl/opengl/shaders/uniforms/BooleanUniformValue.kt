package org.saar.lwjgl.opengl.shaders.uniforms

class BooleanUniformValue(
    override val name: String,
    override var value: Boolean = false
) : BooleanUniform()