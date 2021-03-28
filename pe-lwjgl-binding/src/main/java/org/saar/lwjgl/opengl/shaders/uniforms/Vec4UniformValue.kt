package org.saar.lwjgl.opengl.shaders.uniforms

import org.joml.Vector4fc
import org.saar.maths.JomlDelegates

class Vec4UniformValue(private val name: String) : Vec4Uniform(), UniformValue<Vector4fc> {

    private var vector: Vector4fc by JomlDelegates.CachedVector4f()

    override fun getUniformValue() = this.value

    override fun getName() = this.name

    override fun getValue() = this.vector

    override fun setValue(value: Vector4fc) {
        this.vector = value
    }
}