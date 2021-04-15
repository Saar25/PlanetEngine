package org.saar.gui.style.value

import org.saar.gui.style.property.Coordinate
import org.saar.gui.style.property.Length

fun interface StyleLength : StyleValue {

    fun compute(parentCoordinate: Coordinate, parentLength: Length): Int

}