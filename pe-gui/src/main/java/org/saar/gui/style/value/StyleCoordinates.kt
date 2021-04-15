package org.saar.gui.style.value

import org.saar.gui.style.property.Coordinate
import org.saar.gui.style.property.Length


object StyleCoordinates {

    @JvmStatic
    fun pixels(pixels: Int) =
        StyleCoordinate { parentCoordinate: Coordinate, _: Length, _: Length ->
            parentCoordinate.get() + pixels
        }

    @JvmStatic
    fun percent(percents: Float) =
        StyleCoordinate { parentCoordinate: Coordinate, parentLength: Length, _: Length ->
            parentCoordinate.get() + (parentLength.get() * percents / 100).toInt()
        }

    @JvmStatic
    fun center() =
        StyleCoordinate { parentCoordinate: Coordinate, parentLength: Length, thisLength: Length ->
            parentCoordinate.get() + (parentLength.get() - thisLength.get()) / 2
        }

    @JvmStatic
    fun inherit() = StyleCoordinate { _: Coordinate, _: Length, _: Length -> 0 }

    @JvmStatic
    fun add(a: StyleCoordinate, b: StyleCoordinate) =
        StyleCoordinate { parentCoordinate: Coordinate, parentLength: Length, thisLength: Length ->
            val aCompute = a.compute(parentCoordinate, parentLength, thisLength)
            val bCompute = b.compute(parentCoordinate, parentLength, thisLength)
            aCompute + bCompute
        }

    @JvmStatic
    fun sub(a: StyleCoordinate, b: StyleCoordinate) =
        StyleCoordinate { parentCoordinate: Coordinate, parentLength: Length, thisLength: Length ->
            val aCompute = a.compute(parentCoordinate, parentLength, thisLength)
            val bCompute = b.compute(parentCoordinate, parentLength, thisLength)
            aCompute - bCompute
        }

    @JvmStatic
    fun add(a: StyleCoordinate, b: Int) =
        StyleCoordinate { parentCoordinate: Coordinate, parentLength: Length, thisLength: Length ->
            a.compute(parentCoordinate, parentLength, thisLength) + b
        }

    @JvmStatic
    fun sub(a: StyleCoordinate, b: Int) =
        StyleCoordinate { parentCoordinate: Coordinate, parentLength: Length, thisLength: Length ->
            a.compute(parentCoordinate, parentLength, thisLength) - b
        }
}