package org.saar.gui.position.coordinate

import org.saar.gui.position.PositionerValue
import org.saar.gui.position.length.Length

fun interface CoordinateValue : PositionerValue {

    fun compute(parentCoordinate: Coordinate, parentLength: Length, thisLength: Length): Int

}