package org.saar.gui.style

import org.joml.Vector4f
import org.saar.gui.style.property.*
import org.saar.maths.utils.Vector4

class Style {

    @JvmField
    val colourModifier: Vector4f = Vector4.of(1f)

    @JvmField
    val x: Coordinate = Coordinate()

    @JvmField
    val y: Coordinate = Coordinate()

    @JvmField
    val position: Position = Position(x, y)

    @JvmField
    val width: Length = Length()

    @JvmField
    val height: Length = Length()

    @JvmField
    val dimensions: Dimensions = Dimensions(width, height)

    @JvmField
    val bounds: Bounds = Bounds(position, dimensions)

    @JvmField
    val borders: Borders = Borders()

    @JvmField
    val borderColour: Colour = Colour()

    @JvmField
    val radiuses: Radiuses = Radiuses()

    @JvmField
    val backgroundColour: CornersColours = CornersColours()

}