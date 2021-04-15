package org.saar.gui.position

import org.saar.gui.position.coordinate.ReadonlyCoordinate
import org.saar.gui.position.length.ReadonlyLength

interface IPositioner {
    val x: ReadonlyCoordinate
    val y: ReadonlyCoordinate
    val width: ReadonlyLength
    val height: ReadonlyLength
}