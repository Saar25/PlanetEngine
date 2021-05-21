package org.saar.gui

import org.saar.gui.style.IStyle

object UINullElement : UIElement {

    override val style: IStyle get() = throw IllegalStateException("Cannot get style of null")

}