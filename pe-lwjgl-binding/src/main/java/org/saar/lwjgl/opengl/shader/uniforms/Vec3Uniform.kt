package org.saar.lwjgl.opengl.shader.uniforms

import org.joml.Vector3fc
import org.lwjgl.opengl.GL20

abstract class Vec3Uniform : Uniform {

    final override fun load(location: Int) {
        val value: Vector3fc = this.value
        GL20.glUniform3f(location, value.x(), value.y(), value.z())
    }

    abstract val value: Vector3fc
}