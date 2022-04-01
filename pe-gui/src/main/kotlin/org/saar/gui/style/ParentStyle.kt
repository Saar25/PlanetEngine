package org.saar.gui.style

import org.saar.gui.style.alignment.ReadonlyAlignment
import org.saar.gui.style.arrangement.ReadonlyArrangement
import org.saar.gui.style.backgroundcolour.ReadonlyBackgroundColour
import org.saar.gui.style.border.ReadonlyBorders
import org.saar.gui.style.bordercolour.ReadonlyBorderColour
import org.saar.gui.style.colourmodifier.ReadonlyColourModifier
import org.saar.gui.style.coordinate.ReadonlyCoordinate
import org.saar.gui.style.font.ReadonlyFontFamily
import org.saar.gui.style.fontcolour.ReadonlyFontColour
import org.saar.gui.style.fontsize.ReadonlyFontSize
import org.saar.gui.style.length.ReadonlyLength
import org.saar.gui.style.margin.ReadonlyMargin
import org.saar.gui.style.position.ReadonlyPosition
import org.saar.gui.style.redius.ReadonlyRadius

interface ParentStyle : Style {

    override val position: ReadonlyPosition

    override val margin: ReadonlyMargin

    override val x: ReadonlyCoordinate

    override val y: ReadonlyCoordinate

    override val width: ReadonlyLength

    override val height: ReadonlyLength

    override val fontSize: ReadonlyFontSize

    override val fontColour: ReadonlyFontColour

    override val font: ReadonlyFontFamily

    override val colourModifier: ReadonlyColourModifier

    override val borders: ReadonlyBorders

    override val borderColour: ReadonlyBorderColour

    override val radius: ReadonlyRadius

    override val backgroundColour: ReadonlyBackgroundColour

    val alignment: ReadonlyAlignment

    val arrangement: ReadonlyArrangement
}