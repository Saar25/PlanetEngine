package org.saar.gui.style.value

import org.saar.gui.style.coordinate.ReadonlyCoordinate
import org.saar.gui.style.length.ReadonlyLength

object LengthValues {
    @JvmStatic
    val zero = LengthValue { _: ReadonlyCoordinate, _: ReadonlyLength, _: ReadonlyLength -> 0 }

    @JvmStatic
    val inherit =
        LengthValue { _: ReadonlyCoordinate, parentLength: ReadonlyLength, _: ReadonlyLength -> parentLength.get() }

    @JvmStatic
    fun pixels(pixels: Int) = LengthValue { _: ReadonlyCoordinate, _: ReadonlyLength, _: ReadonlyLength -> pixels }

    @JvmStatic
    fun percent(percents: Float) =
        LengthValue { _: ReadonlyCoordinate, parentLength: ReadonlyLength, _: ReadonlyLength ->
            (parentLength.get() * percents / 100).toInt()
        }

    @JvmStatic
    fun ratio(ratio: Float) =
        LengthValue { _: ReadonlyCoordinate, _: ReadonlyLength, thisOtherLength: ReadonlyLength ->
            (thisOtherLength.get() * ratio).toInt()
        }

    @JvmStatic
    fun add(a: LengthValue, b: LengthValue) =
        LengthValue { parentCoordinate: ReadonlyCoordinate, parentLength: ReadonlyLength, thisOtherLength: ReadonlyLength ->
            val aCompute = a.compute(parentCoordinate, parentLength, thisOtherLength)
            val bCompute = b.compute(parentCoordinate, parentLength, thisOtherLength)
            aCompute + bCompute
        }

    @JvmStatic
    fun sub(a: LengthValue, b: LengthValue) =
        LengthValue { parentCoordinate: ReadonlyCoordinate, parentLength: ReadonlyLength, thisOtherLength: ReadonlyLength ->
            val aCompute = a.compute(parentCoordinate, parentLength, thisOtherLength)
            val bCompute = b.compute(parentCoordinate, parentLength, thisOtherLength)
            aCompute - bCompute
        }

    @JvmStatic
    fun add(a: LengthValue, b: Int) =
        LengthValue { parentCoordinate: ReadonlyCoordinate, parentLength: ReadonlyLength, thisOtherLength: ReadonlyLength ->
            a.compute(parentCoordinate, parentLength, thisOtherLength) + b
        }

    @JvmStatic
    fun sub(a: LengthValue, b: Int) =
        LengthValue { parentCoordinate: ReadonlyCoordinate, parentLength: ReadonlyLength, thisOtherLength: ReadonlyLength ->
            a.compute(parentCoordinate, parentLength, thisOtherLength) - b
        }
}