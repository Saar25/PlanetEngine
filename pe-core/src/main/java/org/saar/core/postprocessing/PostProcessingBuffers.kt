package org.saar.core.postprocessing

import org.saar.lwjgl.opengl.textures.ReadOnlyTexture
import org.saar.lwjgl.opengl.textures.Texture

class PostProcessingBuffers(
    val colour: ReadOnlyTexture,
    val depth: ReadOnlyTexture = Texture.NULL) {

    constructor(colour: ReadOnlyTexture) : this(colour, Texture.NULL)
}