package org.saar.gui.style

import org.joml.Vector4f
import org.saar.gui.style.property.*

interface IStyle {

    val colourModifier: Vector4f

    val x: Coordinate

    val y: Coordinate

    val position: Position

    val width: Length

    val height: Length

    val dimensions: Dimensions

    val bounds: Bounds

    val borders: Borders

    val borderColour: Colour

    val radiuses: Radiuses

    val backgroundColour: CornersColours

}