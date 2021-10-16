package org.saar.lwjgl.opengl.shaders.uniforms

class UniformWrapper(private val location: Int, private val uniform: Uniform) {

    fun load() {
        if (this.location >= 0) {
            this.uniform.load(this.location)
        }
    }

}