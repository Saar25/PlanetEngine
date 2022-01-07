package org.saar.gui

import org.saar.gui.block.UIBlock
import org.saar.gui.style.NoStyle
import org.saar.gui.style.ParentStyle

object UINullElement : UIParentElement {

    override val style: ParentStyle = NoStyle

    override val uiBlock: UIBlock = UIBlock(this.style)

}