package org.saar.gui.style.discardmap

import org.saar.gui.UIChildNode
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D

fun interface DiscardMapValue {

    fun compute(container: UIChildNode): ReadOnlyTexture2D

}