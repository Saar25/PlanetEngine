package org.saar.lwjgl.opengl.shaders.uniforms

import org.joml.Vector3fc
import org.saar.maths.JomlDelegates

class Vec3UniformValue(private val name: String) : Vec3Uniform(), UniformValue<Vector3fc> {

    private var vector: Vector3fc by JomlDelegates.CachedVector3f()

    override fun getUniformValue() = this.value

    override fun getName() = this.name

    override fun getValue() = this.vector

    override fun setValue(value: Vector3fc) {
        this.vector = value
    }
}