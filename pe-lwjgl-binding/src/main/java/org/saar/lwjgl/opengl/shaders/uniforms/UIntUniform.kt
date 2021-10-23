package org.saar.lwjgl.opengl.shaders.uniforms

import org.lwjgl.opengl.GL30

abstract class UIntUniform : Uniform {

    final override fun load(location: Int) {
        val value: Int = this.value
        GL30.glUniform1ui(location, value)
    }

    abstract val value: Int
}