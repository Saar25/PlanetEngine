package org.saar.gui.position.coordinate

import org.saar.gui.position.length.ReadonlyLength

object CoordinateValues {

    @JvmStatic
    val zero = CoordinateValue { _: ReadonlyCoordinate, _: ReadonlyLength, _: ReadonlyLength -> 0 }

    @JvmStatic
    fun pixels(pixels: Int) = CoordinateValue { parentCoordinate: ReadonlyCoordinate,
                                                _: ReadonlyLength,
                                                _: ReadonlyLength ->
        parentCoordinate.get() + pixels
    }

    @JvmStatic
    fun percent(percents: Float) = CoordinateValue { parentCoordinate: ReadonlyCoordinate,
                                                     parentLength: ReadonlyLength,
                                                     _: ReadonlyLength ->
        parentCoordinate.get() + (parentLength.get() * percents / 100).toInt()
    }

    @JvmStatic
    fun center() = CoordinateValue { parentCoordinate: ReadonlyCoordinate,
                                     parentLength: ReadonlyLength,
                                     thisLength: ReadonlyLength ->
        parentCoordinate.get() + (parentLength.get() - thisLength.get()) / 2
    }

    @JvmStatic
    fun add(a: CoordinateValue, b: CoordinateValue) = CoordinateValue { parentCoordinate: ReadonlyCoordinate,
                                                                        parentLength: ReadonlyLength,
                                                                        thisLength: ReadonlyLength ->
        val aCompute = a.compute(parentCoordinate, parentLength, thisLength)
        val bCompute = b.compute(parentCoordinate, parentLength, thisLength)
        aCompute + bCompute
    }

    @JvmStatic
    fun sub(a: CoordinateValue, b: CoordinateValue) = CoordinateValue { parentCoordinate: ReadonlyCoordinate,
                                                                        parentLength: ReadonlyLength,
                                                                        thisLength: ReadonlyLength ->
        val aCompute = a.compute(parentCoordinate, parentLength, thisLength)
        val bCompute = b.compute(parentCoordinate, parentLength, thisLength)
        aCompute - bCompute
    }

    @JvmStatic
    fun add(a: CoordinateValue, b: Int) = CoordinateValue { parentCoordinate: ReadonlyCoordinate,
                                                            parentLength: ReadonlyLength,
                                                            thisLength: ReadonlyLength ->
        a.compute(parentCoordinate, parentLength, thisLength) + b
    }

    @JvmStatic
    fun sub(a: CoordinateValue, b: Int) = CoordinateValue { parentCoordinate: ReadonlyCoordinate,
                                                            parentLength: ReadonlyLength,
                                                            thisLength: ReadonlyLength ->
        a.compute(parentCoordinate, parentLength, thisLength) - b
    }
}