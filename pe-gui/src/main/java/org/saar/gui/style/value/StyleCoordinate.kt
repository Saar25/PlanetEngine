package org.saar.gui.style.value

import org.saar.gui.style.property.Coordinate
import org.saar.gui.style.property.Length

interface StyleCoordinate : StyleValue {

    fun compute(parentCoordinate: Coordinate,
                parentLength: Length,
                thisLength: Length): Int

}