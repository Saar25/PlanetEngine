package org.saar.gui.style.backgroundcolour

import org.saar.gui.style.Colour
import org.saar.gui.style.Colours

object NoBackgroundColour : ReadonlyBackgroundColour {

    override val topRight: Colour = Colours.TRANSPARENT
    override val topLeft: Colour = Colours.TRANSPARENT
    override val bottomRight: Colour = Colours.TRANSPARENT
    override val bottomLeft: Colour = Colours.TRANSPARENT

}