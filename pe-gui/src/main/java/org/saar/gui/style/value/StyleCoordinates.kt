package org.saar.gui.style.value

import org.saar.gui.style.property.Coordinate
import org.saar.gui.style.property.Length


object StyleCoordinates {

    @JvmStatic
    fun pixels(pixels: Int) = object : StyleCoordinate {
        override fun compute(parentCoordinate: Coordinate, parentLength: Length, thisLength: Length): Int {
            return parentCoordinate.get() + parentLength.get() + pixels
        }
    }

    @JvmStatic
    fun percent(percents: Float) = object : StyleCoordinate {
        override fun compute(parentCoordinate: Coordinate, parentLength: Length, thisLength: Length): Int {
            return parentCoordinate.get() + (parentLength.get() * percents / 100).toInt()
        }
    }

    @JvmStatic
    fun center() = object : StyleCoordinate {
        override fun compute(parentCoordinate: Coordinate, parentLength: Length, thisLength: Length): Int {
            return parentCoordinate.get() + (parentLength.get() - thisLength.get()) / 2
        }
    }
}