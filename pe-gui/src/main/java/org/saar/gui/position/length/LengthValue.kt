package org.saar.gui.position.length

import org.saar.gui.position.PositionerValue
import org.saar.gui.position.coordinate.ReadonlyCoordinate

fun interface LengthValue : PositionerValue {

    fun compute(parentCoordinate: ReadonlyCoordinate,
                parentLength: ReadonlyLength): Int

}