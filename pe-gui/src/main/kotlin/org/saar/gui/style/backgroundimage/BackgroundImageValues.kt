package org.saar.gui.style.backgroundimage

import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D
import org.saar.lwjgl.opengl.texture.Texture2D

object BackgroundImageValues {

    @JvmField
    val inherit: BackgroundImageValue = BackgroundImageValue { it.parent.style.backgroundImage.texture }

    @JvmField
    val none: BackgroundImageValue = BackgroundImageValue { Texture2D.NULL }

    @JvmStatic
    fun of(value: ReadOnlyTexture2D): BackgroundImageValue = BackgroundImageValue { value }

}