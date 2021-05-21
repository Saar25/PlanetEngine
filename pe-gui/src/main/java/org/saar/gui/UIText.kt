package org.saar.gui

import org.saar.core.renderer.RenderContext
import org.saar.gui.font.Font
import org.saar.gui.style.Style

class UIText(private val font: Font, private val text: String) : UIChildNode, UIElement {

    override var parent: UIElement = UINullElement

    override val style = Style(this)

    val children = this.text.map { UILetter(this, font, it) }

    override fun render(context: RenderContext) {
        this.children.forEach { it.render(context) }
    }

    override fun delete() = this.children.forEach { it.delete() }
}