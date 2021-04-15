package org.saar.gui.style

import org.joml.Vector4f
import org.saar.gui.position.IPositioner
import org.saar.gui.style.property.Borders
import org.saar.gui.style.property.Colour
import org.saar.gui.style.property.CornersColours
import org.saar.gui.style.property.Radiuses
import org.saar.maths.utils.Vector4

class Style(positioner: IPositioner) : IStyle {

    override val colourModifier: Vector4f = Vector4.of(1f)

    override val borders: Borders = Borders()

    override val borderColour: Colour = Colour()

    override val radiuses: Radiuses = Radiuses(positioner)

    override val backgroundColour: CornersColours = CornersColours()

}