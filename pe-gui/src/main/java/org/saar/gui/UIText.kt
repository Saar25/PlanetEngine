package org.saar.gui

import org.saar.gui.font.Font
import org.saar.gui.style.Style

class UIText(parent: UIElement, font: Font, text: String) : UIContainer, UIChildNode, UIElement {

    override var parent = parent

    override val style = Style(this)

    override val children = text.map { UILetter(this, font, it) }.toList()

    override val uiBlocks = mutableListOf<UIBlock>()
}