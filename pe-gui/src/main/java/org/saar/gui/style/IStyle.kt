package org.saar.gui.style

import org.joml.Vector4fc
import org.saar.gui.style.border.ReadonlyStyleBorders
import org.saar.gui.style.property.Colour
import org.saar.gui.style.property.CornersColours
import org.saar.gui.style.property.Radiuses

interface IStyle {

    val colourModifier: Vector4fc

    val borders: ReadonlyStyleBorders

    val borderColour: Colour

    val radiuses: Radiuses

    val backgroundColour: CornersColours

}