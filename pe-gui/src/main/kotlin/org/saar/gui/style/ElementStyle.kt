package org.saar.gui.style

import org.saar.gui.UIElement
import org.saar.gui.style.alignment.Alignment
import org.saar.gui.style.arrangement.Arrangement
import org.saar.gui.style.axisalignment.AxisAlignment
import org.saar.gui.style.backgroundcolour.BackgroundColour
import org.saar.gui.style.backgroundimage.BackgroundImage
import org.saar.gui.style.border.Borders
import org.saar.gui.style.bordercolour.BorderColour
import org.saar.gui.style.boxsizing.BoxSizing
import org.saar.gui.style.boxsizing.ReadonlyBoxSizing
import org.saar.gui.style.colourmodifier.ColourModifier
import org.saar.gui.style.coordinate.Coordinate
import org.saar.gui.style.coordinate.Coordinates
import org.saar.gui.style.discardmap.DiscardMap
import org.saar.gui.style.font.FontFamily
import org.saar.gui.style.fontcolour.FontColour
import org.saar.gui.style.fontsize.FontSize
import org.saar.gui.style.length.Length
import org.saar.gui.style.length.Lengths
import org.saar.gui.style.margin.Margin
import org.saar.gui.style.opacity.Opacity
import org.saar.gui.style.padding.Padding
import org.saar.gui.style.position.Position
import org.saar.gui.style.redius.Radius

class ElementStyle(container: UIElement) : ParentStyle {

    override val position: Position = Position(container)

    override val margin: Margin = Margin(container)

    override val padding: Padding = Padding(container)

    override val x: Coordinate = Coordinates.X(container)

    override val y: Coordinate = Coordinates.Y(container)

    override val width: Length = Lengths.Width(container)

    override val height: Length = Lengths.Height(container)

    override val boxSizing: ReadonlyBoxSizing = BoxSizing(container)

    override val fontSize: FontSize = FontSize(container)

    override val fontColour: FontColour = FontColour(container)

    override val font: FontFamily = FontFamily(container)

    override val colourModifier: ColourModifier = ColourModifier(container)

    override val borders: Borders = Borders(container)

    override val borderColour: BorderColour = BorderColour(container)

    override val radius: Radius = Radius(container)

    override val opacity: Opacity = Opacity(container)

    override val backgroundColour: BackgroundColour = BackgroundColour(container)

    override val backgroundImage: BackgroundImage = BackgroundImage(container)

    override val discardMap: DiscardMap = DiscardMap(container)

    override val alignment: Alignment = Alignment(container)

    override val arrangement: Arrangement = Arrangement(container)

    override val axisAlignment: AxisAlignment = AxisAlignment(container)
}