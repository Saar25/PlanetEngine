package org.saar.gui.font

import org.joml.Vector2fc
import org.saar.gui.UIChildElement
import org.saar.gui.UIText
import org.saar.gui.style.IStyle
import org.saar.gui.style.Style

class UILetter(override val parent: UIText, val font: Font,
               val character: FontCharacter, val offset: Vector2fc) : UIChildElement {

    override val style: IStyle = Style(this)
}