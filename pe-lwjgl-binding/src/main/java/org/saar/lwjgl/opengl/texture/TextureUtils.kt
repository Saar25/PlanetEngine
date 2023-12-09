package org.saar.lwjgl.opengl.texture

import org.saar.lwjgl.opengl.texture.parameter.*
import org.saar.lwjgl.opengl.texture.values.MagFilterValue
import org.saar.lwjgl.opengl.texture.values.MinFilterValue
import org.saar.lwjgl.opengl.texture.values.WrapValue

object TextureUtils {

    fun MutableTexture.applyDefaultParameters() {
        applyParameters(arrayOf<TextureParameter>(
            TextureMinFilterParameter(MinFilterValue.NEAREST),
            TextureMagFilterParameter(MagFilterValue.NEAREST),
            TextureSWrapParameter(WrapValue.REPEAT),
            TextureTWrapParameter(WrapValue.REPEAT)
        ))
        generateMipmap()
    }
}
