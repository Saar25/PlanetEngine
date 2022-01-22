package org.saar.gui

import org.saar.gui.style.NoStyle

object UINullNode : UIParentNode {

    override val style = NoStyle

    override val children = emptyList<UINode>()

    override fun contains(x: Int, y: Int) = false
}