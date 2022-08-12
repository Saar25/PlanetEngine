package org.saar.lwjgl.opengl.shader.uniforms

import org.joml.Vector3ic
import org.lwjgl.opengl.GL20

abstract class Vec3iUniform : Uniform {

    final override fun load(location: Int) {
        val value: Vector3ic = this.value
        GL20.glUniform3i(location, value.x(), value.y(), value.z())
    }

    abstract val value: Vector3ic
}