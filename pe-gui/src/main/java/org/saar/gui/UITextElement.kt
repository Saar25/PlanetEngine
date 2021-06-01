package org.saar.gui

import org.saar.core.renderer.RenderContext
import org.saar.gui.block.UIBlock
import org.saar.gui.font.Font
import org.saar.gui.font.FontLoader
import org.saar.gui.style.TextStyle

class UITextElement(val font: Font = FontLoader.DEFAULT_FONT, text: String = "") : UIChildElement {

    constructor() : this(FontLoader.DEFAULT_FONT, "")

    constructor(font: Font) : this(font, "")

    constructor(text: String) : this(FontLoader.DEFAULT_FONT, text)

    override val style = TextStyle(this)

    override var parent: UIElement = UINullElement

    override val uiBlock = UIBlock(this.style)

    val uiText: UIText = UIText(this, font, text)

    override fun update() = this.uiText.update()

    override fun render(context: RenderContext) {
        super.render(context)
        this.uiText.render(context)
    }
}