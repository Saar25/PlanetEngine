package org.saar.lwjgl.opengl.shaders.uniforms

import org.lwjgl.opengl.GL20

abstract class FloatUniform : Uniform {

    final override fun load(location: Int) {
        val value: Float = this.value
        GL20.glUniform1f(location, value)
    }

    abstract val value: Float
}