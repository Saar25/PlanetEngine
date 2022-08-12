package org.saar.lwjgl.opengl.shader.uniforms

import org.joml.Vector4fc
import org.lwjgl.opengl.GL20

abstract class Vec4Uniform : Uniform {

    final override fun load(location: Int) {
        val value: Vector4fc = this.value
        GL20.glUniform4f(location, value.x(), value.y(), value.z(), value.w())
    }

    abstract val value: Vector4fc
}