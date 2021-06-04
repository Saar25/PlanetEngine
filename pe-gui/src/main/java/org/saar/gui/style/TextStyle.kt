package org.saar.gui.style

import org.saar.gui.UITextElement
import org.saar.gui.style.backgroundcolour.BackgroundColour
import org.saar.gui.style.border.StyleBorders
import org.saar.gui.style.bordercolour.BorderColour
import org.saar.gui.style.colourmodifier.ColourModifier
import org.saar.gui.style.coordinate.Coordinate
import org.saar.gui.style.coordinate.Coordinates
import org.saar.gui.style.length.Length
import org.saar.gui.style.length.Lengths
import org.saar.gui.style.length.PixelsLength
import org.saar.gui.style.length.ReadonlyLength
import org.saar.gui.style.redius.StyleRadiuses
import org.saar.gui.style.value.LengthValues

class TextStyle(container: UITextElement) : ITextStyle {

    override val x: Coordinate = Coordinates.X(container)

    override val y: Coordinate = Coordinates.Y(container)

    override val width: ReadonlyLength = ReadonlyLength { container.uiText.contentWidth }

    override val height: ReadonlyLength = ReadonlyLength { container.uiText.contentHeight }

    override val fontSize: PixelsLength = PixelsLength(container)

    override val colourModifier: ColourModifier = ColourModifier(container)

    override val borders: StyleBorders = StyleBorders(container)

    override val borderColour: BorderColour = BorderColour(container)

    override val radiuses: StyleRadiuses = StyleRadiuses(container)

    override val backgroundColour: BackgroundColour = BackgroundColour(container)

}