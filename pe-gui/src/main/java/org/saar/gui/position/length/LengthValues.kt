package org.saar.gui.position.length

import org.saar.gui.position.coordinate.ReadonlyCoordinate

object LengthValues {

    @JvmStatic
    val zero = LengthValue { _: ReadonlyCoordinate, _: ReadonlyLength -> 0 }

    @JvmStatic
    fun pixels(pixels: Int) = LengthValue { _: ReadonlyCoordinate, _: ReadonlyLength -> pixels }

    @JvmStatic
    fun percent(percents: Float) =
        LengthValue { _: ReadonlyCoordinate, parentLength: ReadonlyLength ->
            (parentLength.get() * percents / 100).toInt()
        }

    @JvmStatic
    fun inherit() = LengthValue { _: ReadonlyCoordinate, parentLength: ReadonlyLength -> parentLength.get() }

    @JvmStatic
    fun add(a: LengthValue, b: LengthValue) =
        LengthValue { parentCoordinate: ReadonlyCoordinate, parentLength: ReadonlyLength ->
            val aCompute = a.compute(parentCoordinate, parentLength)
            val bCompute = b.compute(parentCoordinate, parentLength)
            aCompute + bCompute
        }

    @JvmStatic
    fun sub(a: LengthValue, b: LengthValue) =
        LengthValue { parentCoordinate: ReadonlyCoordinate, parentLength: ReadonlyLength ->
            val aCompute = a.compute(parentCoordinate, parentLength)
            val bCompute = b.compute(parentCoordinate, parentLength)
            aCompute - bCompute
        }

    @JvmStatic
    fun add(a: LengthValue, b: Int) =
        LengthValue { parentCoordinate: ReadonlyCoordinate, parentLength: ReadonlyLength ->
            a.compute(parentCoordinate, parentLength) + b
        }

    @JvmStatic
    fun sub(a: LengthValue, b: Int) =
        LengthValue { parentCoordinate: ReadonlyCoordinate, parentLength: ReadonlyLength ->
            a.compute(parentCoordinate, parentLength) - b
        }
}