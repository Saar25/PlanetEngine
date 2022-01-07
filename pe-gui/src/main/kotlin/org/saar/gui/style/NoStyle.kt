package org.saar.gui.style

import org.saar.gui.UIChildElement
import org.saar.gui.style.alignment.ReadonlyAlignment
import org.saar.gui.style.backgroundcolour.NoBackgroundColour
import org.saar.gui.style.backgroundcolour.ReadonlyBackgroundColour
import org.saar.gui.style.border.NoStyleBorders
import org.saar.gui.style.border.ReadonlyStyleBorders
import org.saar.gui.style.bordercolour.NoBorderColour
import org.saar.gui.style.bordercolour.ReadonlyBorderColour
import org.saar.gui.style.colourmodifier.NoColourModifier
import org.saar.gui.style.colourmodifier.ReadonlyColourModifier
import org.saar.gui.style.coordinate.ReadonlyCoordinate
import org.saar.gui.style.font.NoStyleFont
import org.saar.gui.style.font.ReadonlyStyleFont
import org.saar.gui.style.fontcolour.NoFontColour
import org.saar.gui.style.fontcolour.ReadonlyFontColour
import org.saar.gui.style.fontsize.NoFontSize
import org.saar.gui.style.fontsize.ReadonlyFontSize
import org.saar.gui.style.length.ReadonlyLength
import org.saar.gui.style.position.ReadonlyPosition
import org.saar.gui.style.redius.NoStyleRadiuses
import org.saar.gui.style.redius.ReadonlyStyleRadiuses

object NoStyle : ParentStyle {

    override val position: ReadonlyPosition = object : ReadonlyPosition {
        override fun getX() = 0

        override fun getY() = 0
    }

    override val x: ReadonlyCoordinate = ReadonlyCoordinate { 0 }

    override val y: ReadonlyCoordinate = ReadonlyCoordinate { 0 }

    override val width: ReadonlyLength = ReadonlyLength { 0 }

    override val height: ReadonlyLength = ReadonlyLength { 0 }

    override val fontSize: ReadonlyFontSize = NoFontSize

    override val fontColour: ReadonlyFontColour = NoFontColour

    override val font: ReadonlyStyleFont = NoStyleFont

    override val colourModifier: ReadonlyColourModifier = NoColourModifier

    override val borders: ReadonlyStyleBorders = NoStyleBorders

    override val borderColour: ReadonlyBorderColour = NoBorderColour

    override val radiuses: ReadonlyStyleRadiuses = NoStyleRadiuses

    override val backgroundColour: ReadonlyBackgroundColour = NoBackgroundColour

    override val alignment: ReadonlyAlignment = object : ReadonlyAlignment {
        override fun getX(child: UIChildElement) = 0

        override fun getY(child: UIChildElement) = 0
    }
}