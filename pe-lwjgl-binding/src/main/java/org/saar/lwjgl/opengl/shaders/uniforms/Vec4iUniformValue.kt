package org.saar.lwjgl.opengl.shaders.uniforms

import org.joml.Vector4ic
import org.saar.maths.JomlDelegates

class Vec4iUniformValue(private val name: String) : Vec4iUniform(), UniformValue<Vector4ic> {

    private var vector: Vector4ic by JomlDelegates.CachedVector4i()

    override fun getUniformValue() = this.value

    override fun getName() = this.name

    override fun getValue() = this.vector

    override fun setValue(value: Vector4ic) {
        this.vector = value
    }
}