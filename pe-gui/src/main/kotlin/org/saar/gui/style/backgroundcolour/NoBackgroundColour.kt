package org.saar.gui.style.backgroundcolour

import org.jproperty.constant.ConstantObjectProperty
import org.saar.gui.style.Colours

object NoBackgroundColour : ReadonlyBackgroundColour {

    override val topRight = ConstantObjectProperty(Colours.TRANSPARENT)
    override val topLeft = ConstantObjectProperty(Colours.TRANSPARENT)
    override val bottomRight = ConstantObjectProperty(Colours.TRANSPARENT)
    override val bottomLeft = ConstantObjectProperty(Colours.TRANSPARENT)

}