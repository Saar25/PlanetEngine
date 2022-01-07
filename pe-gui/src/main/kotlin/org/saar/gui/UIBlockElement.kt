package org.saar.gui

import org.saar.gui.block.UIBlock
import org.saar.gui.style.ElementStyle
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture

class UIBlockElement : UIElement {

    override var parent: UIParentNode = UINullNode

    override val style = ElementStyle(this)

    override val uiBlock = UIBlock(this.style)

    var texture: ReadOnlyTexture? by this.uiBlock::texture

    var discardMap: ReadOnlyTexture? by this.uiBlock::discardMap
}