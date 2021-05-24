package org.saar.gui.style.backgroundcolour

import org.saar.gui.style.Colour
import org.saar.gui.style.Colours

object NoBackgroundColour : ReadonlyBackgroundColour {

    override val topRight: Colour = Colours.BLACK
    override val topLeft: Colour = Colours.BLACK
    override val bottomRight: Colour = Colours.BLACK
    override val bottomLeft: Colour = Colours.BLACK

}