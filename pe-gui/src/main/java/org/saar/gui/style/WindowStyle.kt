package org.saar.gui.style

import org.joml.Vector4f
import org.saar.gui.style.property.*
import org.saar.maths.utils.Vector4

object WindowStyle : IStyle {

    override val colourModifier: Vector4f = Vector4.of(1f)

    override val x: Coordinate = Coordinate.XGenesis()

    override val y: Coordinate = Coordinate.YGenesis()

    override val position: Position = Position(x, y)

    override val width: Length = Length.WidthGenesis()

    override val height: Length = Length.HeightGenesis()

    override val dimensions: Dimensions = Dimensions(width, height)

    override val bounds: Bounds = Bounds(position, dimensions)

    override val borders: Borders = Borders()

    override val borderColour: Colour = Colour()

    override val radiuses: Radiuses = Radiuses(this)

    override val backgroundColour: CornersColours = CornersColours()

}