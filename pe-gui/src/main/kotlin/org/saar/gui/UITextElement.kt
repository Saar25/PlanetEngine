package org.saar.gui

import org.saar.core.renderer.RenderContext
import org.saar.gui.block.UIBlock
import org.saar.gui.block.UIBlockRenderer
import org.saar.gui.style.TextStyle

class UITextElement(text: String = "") : UIChildElement {

    constructor() : this("")

    override val style = TextStyle(this)

    override var parent: UIElement = UINullElement

    override val uiBlock = UIBlock(this.style)

    val uiText: UIText = UIText(this, text)

    override fun update() = this.uiText.update()

    override fun renderText(context: RenderContext) {
        this.uiText.render(context)
    }
}