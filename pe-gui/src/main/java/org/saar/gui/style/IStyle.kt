package org.saar.gui.style

import org.joml.Vector4f
import org.saar.gui.style.property.Borders
import org.saar.gui.style.property.Colour
import org.saar.gui.style.property.CornersColours
import org.saar.gui.style.property.Radiuses

interface IStyle {

    val colourModifier: Vector4f

    val borders: Borders

    val borderColour: Colour

    val radiuses: Radiuses

    val backgroundColour: CornersColours

}