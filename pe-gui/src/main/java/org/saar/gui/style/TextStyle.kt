package org.saar.gui.style

import org.saar.gui.UIText
import org.saar.gui.style.backgroundcolour.BackgroundColour
import org.saar.gui.style.border.StyleBorders
import org.saar.gui.style.bordercolour.BorderColour
import org.saar.gui.style.colourmodifier.ColourModifier
import org.saar.gui.style.coordinate.Coordinate
import org.saar.gui.style.coordinate.Coordinates
import org.saar.gui.style.length.*
import org.saar.gui.style.redius.StyleRadiuses

class TextStyle(container: UIText) : ITextStyle {

    override val x: Coordinate = Coordinates.X(container)

    override val y: Coordinate = Coordinates.Y(container)

    override val width: TextLength = TextLengths.Width(container)

    override val height: TextLength = TextLengths.Height(container)

    override val fontSize: PixelsLength = Lengths.Pixels(container)

    override val contentWidth: Length = Lengths.Width(container)

    override val contentHeight: Length = Lengths.Height(container)

    val bounds: Bounds = Bounds(this)

    override val colourModifier: ColourModifier = ColourModifier(container)

    override val borders: StyleBorders = StyleBorders(container)

    override val borderColour: BorderColour = BorderColour(container)

    override val radiuses: StyleRadiuses = StyleRadiuses(container)

    override val backgroundColour: BackgroundColour = BackgroundColour(container)

}