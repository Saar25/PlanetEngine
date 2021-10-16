package org.saar.lwjgl.opengl.shaders.uniforms

import org.lwjgl.opengl.GL20

abstract class IntUniform : Uniform {

    final override fun load(location: Int) {
        val value: Int = this.value
        GL20.glUniform1i(location, value)
    }

    abstract val value: Int
}