package org.saar.lwjgl.opengl.shaders.uniforms

import org.joml.Vector2ic
import org.lwjgl.opengl.GL20

abstract class Vec2iUniform : Uniform {

    final override fun load(location: Int) {
        val value: Vector2ic = this.value
        GL20.glUniform2i(location, value.x(), value.y())
    }

    abstract val value: Vector2ic
}