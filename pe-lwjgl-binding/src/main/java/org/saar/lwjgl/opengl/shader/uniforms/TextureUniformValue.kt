package org.saar.lwjgl.opengl.shader.uniforms

import org.saar.lwjgl.opengl.texture.ReadOnlyTexture
import org.saar.lwjgl.opengl.texture.Texture2D

class TextureUniformValue(
    override val name: String,
    override var unit: Int,
    override var value: ReadOnlyTexture = Texture2D.NULL,
) : TextureUniform()