package org.saar.gui.position.length

import org.saar.gui.position.PositionerValue
import org.saar.gui.position.coordinate.Coordinate

fun interface LengthValue : PositionerValue {

    fun compute(parentCoordinate: Coordinate, parentLength: Length): Int

}