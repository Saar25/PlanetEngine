package org.saar.gui.style.value

import org.saar.gui.style.PositionerValue
import org.saar.gui.style.coordinate.ReadonlyCoordinate
import org.saar.gui.style.length.ReadonlyLength

fun interface CoordinateValue : PositionerValue {

    fun compute(parentCoordinate: ReadonlyCoordinate,
                parentLength: ReadonlyLength,
                thisLength: ReadonlyLength): Int

}