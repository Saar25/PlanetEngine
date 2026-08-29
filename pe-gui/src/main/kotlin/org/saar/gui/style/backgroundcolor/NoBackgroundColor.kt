package org.saar.gui.style.backgroundcolor

import org.saar.gui.style.Colors

object NoBackgroundColor : ReadonlyBackgroundColor {

    override val topRight = Colors.TRANSPARENT
    override val topLeft = Colors.TRANSPARENT
    override val bottomRight = Colors.TRANSPARENT
    override val bottomLeft = Colors.TRANSPARENT

}