package org.saar.gui.style

import org.saar.gui.UIChildElement
import org.saar.gui.style.backgroundcolour.BackgroundColour
import org.saar.gui.style.border.StyleBorders
import org.saar.gui.style.bordercolour.BorderColour
import org.saar.gui.style.colourmodifier.ColourModifier
import org.saar.gui.style.coordinate.Coordinate
import org.saar.gui.style.coordinate.Coordinates
import org.saar.gui.style.font.StyleFont
import org.saar.gui.style.fontsize.FontSize
import org.saar.gui.style.length.Length
import org.saar.gui.style.length.Lengths
import org.saar.gui.style.redius.StyleRadiuses

class Style(container: UIChildElement) : IStyle {

    override val x: Coordinate = Coordinates.X(container)

    override val y: Coordinate = Coordinates.Y(container)

    override val width: Length = Lengths.Width(container)

    override val height: Length = Lengths.Height(container)

    override val fontSize: FontSize = FontSize(container)

    override val font: StyleFont = StyleFont(container)

    override val colourModifier: ColourModifier = ColourModifier(container)

    override val borders: StyleBorders = StyleBorders(container)

    override val borderColour: BorderColour = BorderColour(container)

    override val radiuses: StyleRadiuses = StyleRadiuses(container)

    override val backgroundColour: BackgroundColour = BackgroundColour(container)
}