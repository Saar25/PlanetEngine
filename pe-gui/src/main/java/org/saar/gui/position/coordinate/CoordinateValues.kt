package org.saar.gui.position.coordinate

import org.saar.gui.position.length.Length

object CoordinateValues {

    @JvmStatic
    val zero = CoordinateValue { _: Coordinate, _: Length, _: Length -> 0 }

    @JvmStatic
    fun pixels(pixels: Int) =
        CoordinateValue { parentCoordinate: Coordinate, _: Length, _: Length ->
            parentCoordinate.get() + pixels
        }

    @JvmStatic
    fun percent(percents: Float) =
        CoordinateValue { parentCoordinate: Coordinate, parentLength: Length, _: Length ->
            parentCoordinate.get() + (parentLength.get() * percents / 100).toInt()
        }

    @JvmStatic
    fun center() =
        CoordinateValue { parentCoordinate: Coordinate, parentLength: Length, thisLength: Length ->
            parentCoordinate.get() + (parentLength.get() - thisLength.get()) / 2
        }

    @JvmStatic
    fun inherit() = CoordinateValue { _: Coordinate, _: Length, _: Length -> 0 }

    @JvmStatic
    fun add(a: CoordinateValue, b: CoordinateValue) =
        CoordinateValue { parentCoordinate: Coordinate, parentLength: Length, thisLength: Length ->
            val aCompute = a.compute(parentCoordinate, parentLength, thisLength)
            val bCompute = b.compute(parentCoordinate, parentLength, thisLength)
            aCompute + bCompute
        }

    @JvmStatic
    fun sub(a: CoordinateValue, b: CoordinateValue) =
        CoordinateValue { parentCoordinate: Coordinate, parentLength: Length, thisLength: Length ->
            val aCompute = a.compute(parentCoordinate, parentLength, thisLength)
            val bCompute = b.compute(parentCoordinate, parentLength, thisLength)
            aCompute - bCompute
        }

    @JvmStatic
    fun add(a: CoordinateValue, b: Int) =
        CoordinateValue { parentCoordinate: Coordinate, parentLength: Length, thisLength: Length ->
            a.compute(parentCoordinate, parentLength, thisLength) + b
        }

    @JvmStatic
    fun sub(a: CoordinateValue, b: Int) =
        CoordinateValue { parentCoordinate: Coordinate, parentLength: Length, thisLength: Length ->
            a.compute(parentCoordinate, parentLength, thisLength) - b
        }
}