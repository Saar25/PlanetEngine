package org.saar.lwjgl.opengl.shader.uniforms

import org.joml.Vector2fc
import org.lwjgl.opengl.GL20

abstract class Vec2Uniform : Uniform {

    final override fun load(location: Int) {
        val value: Vector2fc = this.value
        GL20.glUniform2f(location, value.x(), value.y())
    }

    abstract val value: Vector2fc
}