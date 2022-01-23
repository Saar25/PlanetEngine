package org.saar.gui.style

import org.saar.gui.UIBlock
import org.saar.gui.style.backgroundcolour.BackgroundColour
import org.saar.gui.style.border.Borders
import org.saar.gui.style.bordercolour.BorderColour
import org.saar.gui.style.colourmodifier.ColourModifier
import org.saar.gui.style.coordinate.Coordinate
import org.saar.gui.style.coordinate.Coordinates
import org.saar.gui.style.font.FontFamily
import org.saar.gui.style.fontcolour.FontColour
import org.saar.gui.style.fontsize.FontSize
import org.saar.gui.style.length.Length
import org.saar.gui.style.length.Lengths
import org.saar.gui.style.margin.Margin
import org.saar.gui.style.position.Position
import org.saar.gui.style.redius.Radius
import org.saar.gui.style.length.LengthValues

class BlockStyle(container: UIBlock) : Style {

    override val position: Position = Position(container)

    override val margin: Margin = Margin(container)

    override val x: Coordinate = Coordinates.X(container)

    override val y: Coordinate = Coordinates.Y(container)

    override val width: Length = Lengths.Width(container).apply {
        value = LengthValues.fill
    }

    override val height: Length = Lengths.Height(container).apply {
        value = LengthValues.fill
    }

    override val fontSize: FontSize = FontSize(container)

    override val fontColour: FontColour = FontColour(container)

    override val font: FontFamily = FontFamily(container)

    override val colourModifier: ColourModifier = ColourModifier(container)

    override val borders: Borders = Borders(container)

    override val borderColour: BorderColour = BorderColour(container)

    override val radius: Radius = Radius(container)

    override val backgroundColour: BackgroundColour = BackgroundColour(container)
}