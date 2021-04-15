package org.saar.gui.style.value

import org.saar.gui.style.property.Coordinate
import org.saar.gui.style.property.Length


object StyleLengths {

    @JvmStatic
    fun pixels(pixels: Int) = StyleLength { _: Coordinate, _: Length -> pixels }

    @JvmStatic
    fun percent(percents: Float) =
        StyleLength { _: Coordinate, parentLength: Length ->
            (parentLength.get() * percents / 100).toInt()
        }

    @JvmStatic
    fun inherit() = StyleLength { _: Coordinate, parentLength: Length -> parentLength.get() }

    @JvmStatic
    fun add(a: StyleLength, b: StyleLength) =
        StyleLength { parentCoordinate: Coordinate, parentLength: Length ->
            val aCompute = a.compute(parentCoordinate, parentLength)
            val bCompute = b.compute(parentCoordinate, parentLength)
            aCompute + bCompute
        }

    @JvmStatic
    fun sub(a: StyleLength, b: StyleLength) =
        StyleLength { parentCoordinate: Coordinate, parentLength: Length ->
            val aCompute = a.compute(parentCoordinate, parentLength)
            val bCompute = b.compute(parentCoordinate, parentLength)
            aCompute - bCompute
        }

    @JvmStatic
    fun add(a: StyleLength, b: Int) =
        StyleLength { parentCoordinate: Coordinate, parentLength: Length ->
            a.compute(parentCoordinate, parentLength) + b
        }

    @JvmStatic
    fun sub(a: StyleLength, b: Int) =
        StyleLength { parentCoordinate: Coordinate, parentLength: Length ->
            a.compute(parentCoordinate, parentLength) - b
        }
}