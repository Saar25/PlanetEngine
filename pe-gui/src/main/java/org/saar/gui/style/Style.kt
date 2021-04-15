package org.saar.gui.style

import org.joml.Vector4f
import org.saar.gui.style.property.*
import org.saar.maths.utils.Vector4

class Style(private val parentStyle: IStyle = GenesisStyle) : IStyle {

    override val colourModifier: Vector4f = Vector4.of(1f)

    override val x: Coordinate = Coordinate(this.parentStyle, this)

    override val y: Coordinate = Coordinate(this.parentStyle, this)

    override val position: Position = Position(x, y)

    override val width: Length = Length(this.parentStyle, this)

    override val height: Length = Length(this.parentStyle, this)

    override val dimensions: Dimensions = Dimensions(width, height)

    override val bounds: Bounds = Bounds(position, dimensions)

    override val borders: Borders = Borders(this.parentStyle, this)

    override val borderColour: Colour = Colour()

    override val radiuses: Radiuses = Radiuses(this.parentStyle, this)

    override val backgroundColour: CornersColours = CornersColours()

}