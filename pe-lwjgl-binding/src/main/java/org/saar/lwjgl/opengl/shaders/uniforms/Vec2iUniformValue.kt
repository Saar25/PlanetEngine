package org.saar.lwjgl.opengl.shaders.uniforms

import org.joml.Vector2ic
import org.saar.maths.JomlDelegates

class Vec2iUniformValue(private val name: String) : Vec2iUniform(), UniformValue<Vector2ic> {

    private var vector: Vector2ic by JomlDelegates.CachedVector2i()

    override fun getUniformValue() = this.value

    override fun getName() = this.name

    override fun getValue() = this.vector

    override fun setValue(value: Vector2ic) {
        this.vector = value
    }
}