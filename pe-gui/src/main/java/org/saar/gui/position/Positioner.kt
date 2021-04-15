package org.saar.gui.position

import org.saar.gui.UIComponent
import org.saar.gui.position.coordinate.Coordinate
import org.saar.gui.position.coordinate.Coordinates
import org.saar.gui.position.length.Length
import org.saar.gui.position.length.Lengths

class Positioner(container: UIComponent) {

    val x: Coordinate = Coordinates.X(container)

    val y: Coordinate = Coordinates.Y(container)

    val width: Length = Lengths.Width(container)

    val height: Length = Lengths.Height(container)
}