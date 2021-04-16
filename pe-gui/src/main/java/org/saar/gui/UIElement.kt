package org.saar.gui

import org.saar.gui.position.IPositioner
import org.saar.gui.style.IStyle

interface UIElement {

    val positioner: IPositioner

    val style: IStyle

}