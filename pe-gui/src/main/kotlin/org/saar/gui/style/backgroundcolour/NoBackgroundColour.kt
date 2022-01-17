package org.saar.gui.style.backgroundcolour

import org.jproperty.constant.ConstantObject
import org.saar.gui.style.Colours

object NoBackgroundColour : ReadonlyBackgroundColour {

    override val topRight = ConstantObject(Colours.TRANSPARENT)
    override val topLeft = ConstantObject(Colours.TRANSPARENT)
    override val bottomRight = ConstantObject(Colours.TRANSPARENT)
    override val bottomLeft = ConstantObject(Colours.TRANSPARENT)

}