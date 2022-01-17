package org.saar.gui.style

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

interface Style {

    val position: ReadonlyPosition

    val margin: ReadonlyMargin

    val x: ReadonlyCoordinate

    val y: ReadonlyCoordinate

    val width: ReadonlyLength

    val height: ReadonlyLength

    val fontSize: ReadonlyFontSize

    val fontColour: ReadonlyFontColour

    val font: ReadonlyFontFamily

    val colourModifier: ReadonlyColourModifier

    val borders: ReadonlyBorders

    val borderColour: ReadonlyBorderColour

    val radius: ReadonlyRadius

    val backgroundColour: ReadonlyBackgroundColour
}