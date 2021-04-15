package org.saar.gui.position.coordinate

import org.saar.gui.position.PositionerValue
import org.saar.gui.position.length.ReadonlyLength

fun interface CoordinateValue : PositionerValue {

    fun compute(parentCoordinate: ReadonlyCoordinate,
                parentLength: ReadonlyLength,
                thisLength: ReadonlyLength): Int

}