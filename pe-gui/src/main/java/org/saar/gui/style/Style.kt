package org.saar.gui.style

import org.joml.Vector4f
import org.saar.gui.style.property.*
import org.saar.maths.utils.Vector4

class Style(private val parentStyle: Style?) {

    @JvmField
    val colourModifier: Vector4f = Vector4.of(1f)

    @JvmField
    val x: Coordinate = Coordinate(this.parentStyle, this)

    @JvmField
    val y: Coordinate = Coordinate(this.parentStyle, this)

    @JvmField
    val position: Position = Position(x, y)

    @JvmField
    val width: Length = Length(this.parentStyle, this)

    @JvmField
    val height: Length = Length(this.parentStyle, this)

    @JvmField
    val dimensions: Dimensions = Dimensions(width, height)

    @JvmField
    val bounds: Bounds = Bounds(position, dimensions)

    @JvmField
    val borders: Borders = Borders(this.parentStyle, this)

    @JvmField
    val borderColour: Colour = Colour()

    @JvmField
    val radiuses: Radiuses = Radiuses(this.parentStyle, this)

    @JvmField
    val backgroundColour: CornersColours = CornersColours()

}