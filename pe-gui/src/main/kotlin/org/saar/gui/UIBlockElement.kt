package org.saar.gui

import org.saar.gui.block.UIBlock
import org.saar.gui.style.Style
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture

class UIBlockElement : UIChildElement {

    override var parent: UIElement = UINullElement

    override val style = Style(this)

    override val uiBlock = UIBlock(this.style)

    var texture: ReadOnlyTexture? by this.uiBlock::texture

    var discardMap: ReadOnlyTexture? by this.uiBlock::discardMap
}