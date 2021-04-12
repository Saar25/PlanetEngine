package org.saar.lwjgl.opengl.shaders.uniforms

import org.joml.Matrix4fc
import org.saar.maths.JomlDelegates

class Mat4UniformValue(private val name: String) : Mat4Uniform(), UniformValue<Matrix4fc> {

    private var matrix: Matrix4fc by JomlDelegates.CachedMatrix4f()

    override fun getUniformValue() = this.value

    override fun getName() = this.name

    override fun getValue() = this.matrix

    override fun setValue(value: Matrix4fc) {
        this.matrix = value
    }
}