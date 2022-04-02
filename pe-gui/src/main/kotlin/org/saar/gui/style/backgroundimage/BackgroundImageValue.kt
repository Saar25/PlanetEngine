package org.saar.gui.style.backgroundimage

import org.saar.gui.UIChildNode
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D

fun interface BackgroundImageValue {

    fun compute(container: UIChildNode): ReadOnlyTexture2D

}