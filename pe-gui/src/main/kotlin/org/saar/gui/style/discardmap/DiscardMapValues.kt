package org.saar.gui.style.discardmap

import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D
import org.saar.lwjgl.opengl.texture.Texture2D

object DiscardMapValues {

    @JvmField
    val inherit: DiscardMapValue = DiscardMapValue { it.parent.style.discardMap.texture }

    @JvmField
    val none: DiscardMapValue = DiscardMapValue { Texture2D.NULL }

    @JvmStatic
    fun of(value: ReadOnlyTexture2D): DiscardMapValue = DiscardMapValue { value }

}