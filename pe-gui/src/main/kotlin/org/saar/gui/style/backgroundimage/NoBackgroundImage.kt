package org.saar.gui.style.backgroundimage

import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D
import org.saar.lwjgl.opengl.texture.Texture2D

object NoBackgroundImage : ReadonlyBackgroundImage {

    override val texture: ReadOnlyTexture2D = Texture2D.NULL

}