package org.saar.gui.style.discardmap

import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D
import org.saar.lwjgl.opengl.texture.Texture2D

object NoDiscardMap : ReadonlyDiscardMap {

    override val texture: ReadOnlyTexture2D = Texture2D.NULL

}