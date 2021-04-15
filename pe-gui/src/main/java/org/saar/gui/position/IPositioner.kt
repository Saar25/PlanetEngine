package org.saar.gui.position

import org.saar.gui.position.coordinate.Coordinate
import org.saar.gui.position.length.Length

interface IPositioner {
    val x: Coordinate
    val y: Coordinate
    val width: Length
    val height: Length
}