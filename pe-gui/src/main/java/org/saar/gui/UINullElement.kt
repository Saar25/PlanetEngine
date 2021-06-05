package org.saar.gui

import org.saar.gui.block.UIBlock
import org.saar.gui.style.IStyle
import org.saar.gui.style.NoStyle

object UINullElement : UIElement {

    override val style: IStyle = NoStyle

    override val uiBlock: UIBlock = UIBlock(this.style)

}