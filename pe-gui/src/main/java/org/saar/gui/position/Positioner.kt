package org.saar.gui.position

import org.saar.gui.UIComponent
import org.saar.gui.position.coordinate.Coordinate
import org.saar.gui.position.coordinate.Coordinates
import org.saar.gui.position.length.Length
import org.saar.gui.position.length.Lengths

class Positioner(container: UIComponent) : IPositioner {

    override val x: Coordinate = Coordinates.X(container)

    override val y: Coordinate = Coordinates.Y(container)

    override val width: Length = Lengths.Width(container)

    override val height: Length = Lengths.Height(container)

    val bounds: Bounds = Bounds(this)
}