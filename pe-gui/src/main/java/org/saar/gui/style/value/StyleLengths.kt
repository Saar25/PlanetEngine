package org.saar.gui.style.value

import org.saar.gui.style.property.Coordinate
import org.saar.gui.style.property.Length


object StyleLengths {

    @JvmStatic
    fun pixels(pixels: Int) = object : StyleLength {
        override fun compute(parentCoordinate: Coordinate, parentLength: Length): Int {
            return parentCoordinate.get() + parentLength.get() + pixels
        }
    }

    @JvmStatic
    fun percent(percents: Float) = object : StyleLength {
        override fun compute(parentCoordinate: Coordinate, parentLength: Length): Int {
            return parentCoordinate.get() + (parentLength.get() * percents / 100).toInt()
        }
    }
}