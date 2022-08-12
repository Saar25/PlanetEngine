package org.saar.lwjgl.opengl.shader.uniforms

import org.joml.Vector4ic
import org.lwjgl.opengl.GL20

abstract class Vec4iUniform : Uniform {

    final override fun load(location: Int) {
        val value: Vector4ic = this.value
        GL20.glUniform4i(location, value.x(), value.y(), value.z(), value.w())
    }

    abstract val value: Vector4ic
}