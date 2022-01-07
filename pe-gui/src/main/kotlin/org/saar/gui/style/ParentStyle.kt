package org.saar.gui.style

import org.saar.gui.style.alignment.ReadonlyAlignment
import org.saar.gui.style.backgroundcolour.ReadonlyBackgroundColour
import org.saar.gui.style.border.ReadonlyStyleBorders
import org.saar.gui.style.bordercolour.ReadonlyBorderColour
import org.saar.gui.style.colourmodifier.ReadonlyColourModifier
import org.saar.gui.style.coordinate.ReadonlyCoordinate
import org.saar.gui.style.font.ReadonlyStyleFont
import org.saar.gui.style.fontcolour.ReadonlyFontColour
import org.saar.gui.style.fontsize.ReadonlyFontSize
import org.saar.gui.style.length.ReadonlyLength
import org.saar.gui.style.position.ReadonlyPosition
import org.saar.gui.style.redius.ReadonlyStyleRadiuses

interface ParentStyle : IStyle {

    override val position: ReadonlyPosition

    override val x: ReadonlyCoordinate

    override val y: ReadonlyCoordinate

    override val width: ReadonlyLength

    override val height: ReadonlyLength

    override val fontSize: ReadonlyFontSize

    override val fontColour: ReadonlyFontColour

    override val font: ReadonlyStyleFont

    override val colourModifier: ReadonlyColourModifier

    override val borders: ReadonlyStyleBorders

    override val borderColour: ReadonlyBorderColour

    override val radiuses: ReadonlyStyleRadiuses

    override val backgroundColour: ReadonlyBackgroundColour

    val alignment: ReadonlyAlignment

}