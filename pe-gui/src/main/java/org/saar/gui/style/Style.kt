package org.saar.gui.style

import org.joml.Vector4f
import org.saar.gui.UIChildElement
import org.saar.gui.style.backgroundcolour.BackgroundColour
import org.saar.gui.style.border.StyleBorders
import org.saar.gui.style.bordercolour.BorderColour
import org.saar.gui.style.coordinate.Coordinate
import org.saar.gui.style.coordinate.Coordinates
import org.saar.gui.style.length.Length
import org.saar.gui.style.length.Lengths
import org.saar.gui.style.redius.StyleRadiuses
import org.saar.maths.utils.Vector4

class Style(container: UIChildElement) : IStyle {

    override val x: Coordinate = Coordinates.X(container)

    override val y: Coordinate = Coordinates.Y(container)

    override val width: Length = Lengths.Width(container)

    override val height: Length = Lengths.Height(container)

    val bounds: Bounds = Bounds(this)

    override val colourModifier: Vector4f = Vector4.of(1f)

    override val borders: StyleBorders = StyleBorders(container)

    override val borderColour: BorderColour = BorderColour(container)

    override val radiuses: StyleRadiuses = StyleRadiuses(container)

    override val backgroundColour: BackgroundColour = BackgroundColour(container)

}