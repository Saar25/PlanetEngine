package org.saar.lwjgl.opengl.shader.uniforms

import org.lwjgl.opengl.GL20

abstract class BooleanUniform : Uniform {

    final override fun load(location: Int) {
        val value: Boolean = this.value
        GL20.glUniform1i(location, if (value) 1 else 0)
    }

    abstract val value: Boolean
}