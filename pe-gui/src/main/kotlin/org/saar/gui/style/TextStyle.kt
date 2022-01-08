package org.saar.gui.style

import org.saar.gui.UIText
import org.saar.gui.style.backgroundcolour.BackgroundColour
import org.saar.gui.style.border.StyleBorders
import org.saar.gui.style.bordercolour.BorderColour
import org.saar.gui.style.colourmodifier.ColourModifier
import org.saar.gui.style.coordinate.Coordinate
import org.saar.gui.style.coordinate.Coordinates
import org.saar.gui.style.font.StyleFont
import org.saar.gui.style.fontcolour.FontColour
import org.saar.gui.style.fontsize.FontSize
import org.saar.gui.style.length.ReadonlyLength
import org.saar.gui.style.margin.NoMargin
import org.saar.gui.style.margin.ReadonlyMargin
import org.saar.gui.style.position.Position
import org.saar.gui.style.redius.StyleRadiuses

class TextStyle(container: UIText) : Style {

    override val position: Position = Position(container)

    override val margin: ReadonlyMargin = NoMargin

    override val x: Coordinate = Coordinates.X(container)

    override val y: Coordinate = Coordinates.Y(container)

    override val width: ReadonlyLength = ReadonlyLength { container.contentWidth }

    override val height: ReadonlyLength = ReadonlyLength { container.contentHeight }

    override val fontSize: FontSize = FontSize(container)

    override val fontColour: FontColour = FontColour(container)

    override val font: StyleFont = StyleFont(container)

    override val colourModifier: ColourModifier = ColourModifier(container)

    override val borders: StyleBorders = StyleBorders(container)

    override val borderColour: BorderColour = BorderColour(container)

    override val radiuses: StyleRadiuses = StyleRadiuses(container)

    override val backgroundColour: BackgroundColour = BackgroundColour(container)

}