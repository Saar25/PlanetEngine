package org.saar.lwjgl.opengl.shader.uniforms

import org.lwjgl.opengl.GL20
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture

abstract class TextureUniform : Uniform {

    final override fun load(location: Int) {
        GL20.glUniform1i(location, this.unit)
        this.value.bind(this.unit)
    }

    abstract val value: ReadOnlyTexture
    abstract val unit: Int
}