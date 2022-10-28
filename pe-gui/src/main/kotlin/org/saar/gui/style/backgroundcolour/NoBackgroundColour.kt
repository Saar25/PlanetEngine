package org.saar.gui.style.backgroundcolour

import org.saar.gui.style.Colours

object NoBackgroundColour : ReadonlyBackgroundColour {

    override val topRight = Colours.TRANSPARENT
    override val topLeft = Colours.TRANSPARENT
    override val bottomRight = Colours.TRANSPARENT
    override val bottomLeft = Colours.TRANSPARENT

}