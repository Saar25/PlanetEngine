package org.saar.gui.font

import org.joml.Vector2fc
import org.saar.gui.UIText
import org.saar.gui.style.Style

class UILetter(
    val parent: UIText, val font: Font,
    val character: FontCharacter, val offset: Vector2fc,
) {

    val style: Style get() = this.parent.style
}