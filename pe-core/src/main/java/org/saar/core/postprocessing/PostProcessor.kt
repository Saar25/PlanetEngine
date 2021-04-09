package org.saar.core.postprocessing

import org.saar.lwjgl.opengl.textures.ReadOnlyTexture

interface PostProcessor {

    fun process(image: ReadOnlyTexture)

    fun delete()

}