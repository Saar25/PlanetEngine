package org.saar.gui.style.coordinate

abstract class Coordinate : ReadonlyCoordinate {

    var value: CoordinateValue = CoordinateValues.zero

    fun set(value: CoordinateValue) {
        this.value = value
    }

    fun set(pixels: Int) {
        this.value = CoordinateValues.pixels(pixels)
    }

    fun add(value: CoordinateValue) {
        this.value = CoordinateValues.add(this.value, value)
    }

    fun sub(value: CoordinateValue) {
        this.value = CoordinateValues.sub(this.value, value)
    }
}