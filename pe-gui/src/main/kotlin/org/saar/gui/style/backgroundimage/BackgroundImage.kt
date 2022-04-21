package org.saar.gui.style.backgroundimage

import org.saar.gui.UIChildNode
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D

class BackgroundImage(private val container: UIChildNode, default: BackgroundImageValue = BackgroundImageValues.inherit) : ReadonlyBackgroundImage {

    var value: BackgroundImageValue = default

    override val texture get() = this.value.compute(this.container)

    fun set(value: BackgroundImageValue) {
        this.value = value
    }

    fun set(value: ReadOnlyTexture2D) {
        this.value = BackgroundImageValues.of(value)
    }
}