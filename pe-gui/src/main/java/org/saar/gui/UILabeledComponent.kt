package org.saar.gui

import org.saar.core.renderer.RenderContext
import org.saar.gui.font.FontLoader
import org.saar.gui.style.value.CoordinateValues.center
import org.saar.gui.style.value.TextLengthValues.fitContent
import org.saar.gui.style.value.TextLengthValues.pixels

abstract class UILabeledComponent : UIComponent() {

    val label: UIText = UIText(FontLoader.DEFAULT_FONT).apply {
        style.x.value = center()
        style.y.value = center()
        style.width.value = fitContent()
        style.height.value = pixels(70)
    }

    override fun render(context: RenderContext) {
        super.render(context)
        this.label.parent = this
        this.label.render(context)
    }
}