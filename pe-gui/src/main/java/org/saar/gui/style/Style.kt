package org.saar.gui.style

import org.joml.Vector4f
import org.saar.gui.UIChildElement
import org.saar.gui.style.border.StyleBorders
import org.saar.gui.style.property.Colour
import org.saar.gui.style.property.CornersColours
import org.saar.gui.style.redius.StyleRadiuses
import org.saar.maths.utils.Vector4

class Style(container: UIChildElement) : IStyle {

    override val colourModifier: Vector4f = Vector4.of(1f)

    override val borders: StyleBorders = StyleBorders(container)

    override val borderColour: Colour = Colour()

    override val radiuses: StyleRadiuses = StyleRadiuses(container)

    override val backgroundColour: CornersColours = CornersColours()

}