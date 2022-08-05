package org.saar.lwjgl.opengl.fbos.attachment.allocation

import org.saar.lwjgl.opengl.texture.MutableTexture2D

interface TextureAllocationStrategy {

    fun allocate(texture: MutableTexture2D, width: Int, height: Int)

}