package org.saar.gui.style.discardmap

import org.saar.gui.UIChildNode
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D

class DiscardMap(private val container: UIChildNode, default: DiscardMapValue = DiscardMapValues.inherit) : ReadonlyDiscardMap {

    var value: DiscardMapValue = default

    override val texture get() = this.value.compute(this.container)

    fun set(value: DiscardMapValue) {
        this.value = value
    }

    fun set(value: ReadOnlyTexture2D) {
        this.value = DiscardMapValues.of(value)
    }
}