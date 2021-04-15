package org.saar.gui.position.length

import org.saar.gui.position.coordinate.Coordinate

object LengthValues {

    @JvmStatic
    val zero = LengthValue { _: Coordinate, _: Length -> 0 }

    @JvmStatic
    fun pixels(pixels: Int) = LengthValue { _: Coordinate, _: Length -> pixels }

    @JvmStatic
    fun percent(percents: Float) =
        LengthValue { _: Coordinate, parentLength: Length ->
            (parentLength.get() * percents / 100).toInt()
        }

    @JvmStatic
    fun inherit() = LengthValue { _: Coordinate, parentLength: Length -> parentLength.get() }

    @JvmStatic
    fun add(a: LengthValue, b: LengthValue) =
        LengthValue { parentCoordinate: Coordinate, parentLength: Length ->
            val aCompute = a.compute(parentCoordinate, parentLength)
            val bCompute = b.compute(parentCoordinate, parentLength)
            aCompute + bCompute
        }

    @JvmStatic
    fun sub(a: LengthValue, b: LengthValue) =
        LengthValue { parentCoordinate: Coordinate, parentLength: Length ->
            val aCompute = a.compute(parentCoordinate, parentLength)
            val bCompute = b.compute(parentCoordinate, parentLength)
            aCompute - bCompute
        }

    @JvmStatic
    fun add(a: LengthValue, b: Int) =
        LengthValue { parentCoordinate: Coordinate, parentLength: Length ->
            a.compute(parentCoordinate, parentLength) + b
        }

    @JvmStatic
    fun sub(a: LengthValue, b: Int) =
        LengthValue { parentCoordinate: Coordinate, parentLength: Length ->
            a.compute(parentCoordinate, parentLength) - b
        }
}