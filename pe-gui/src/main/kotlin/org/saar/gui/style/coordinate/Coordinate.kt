package org.saar.gui.style.coordinate

abstract class Coordinate(default: CoordinateValue = CoordinateValues.zero) : ReadonlyCoordinate {

    var value: CoordinateValue = default

    fun set(value: CoordinateValue) {
        this.value = value
    }

    fun set(pixels: Int) {
        this.value = CoordinateValues.pixels(pixels)
    }
}