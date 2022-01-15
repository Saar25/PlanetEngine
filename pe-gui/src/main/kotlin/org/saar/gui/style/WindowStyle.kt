package org.saar.gui.style

import org.saar.gui.UIDisplay
import org.saar.gui.font.FontLoader
import org.saar.gui.style.alignment.Alignment
import org.saar.gui.style.backgroundcolour.NoBackgroundColour
import org.saar.gui.style.backgroundcolour.ReadonlyBackgroundColour
import org.saar.gui.style.border.NoStyleBorders
import org.saar.gui.style.border.ReadonlyStyleBorders
import org.saar.gui.style.bordercolour.NoBorderColour
import org.saar.gui.style.bordercolour.ReadonlyBorderColour
import org.saar.gui.style.colourmodifier.NoColourModifier
import org.saar.gui.style.colourmodifier.ReadonlyColourModifier
import org.saar.gui.style.coordinate.ReadonlyCoordinate
import org.saar.gui.style.font.ReadonlyStyleFont
import org.saar.gui.style.fontcolour.NoFontColour
import org.saar.gui.style.fontcolour.ReadonlyFontColour
import org.saar.gui.style.fontsize.ReadonlyFontSize
import org.saar.gui.style.length.ReadonlyLength
import org.saar.gui.style.margin.NoMargin
import org.saar.gui.style.margin.ReadonlyMargin
import org.saar.gui.style.position.ReadonlyPosition
import org.saar.gui.style.redius.NoRadius
import org.saar.gui.style.redius.ReadonlyRadius

class WindowStyle(uiDisplay: UIDisplay) : ParentStyle {

    override val position: ReadonlyPosition = object : ReadonlyPosition {
        override fun getX() = 0

        override fun getY() = 0
    }

    override val margin: ReadonlyMargin = NoMargin

    override val x: ReadonlyCoordinate = ReadonlyCoordinate { 0 }

    override val y: ReadonlyCoordinate = ReadonlyCoordinate { 0 }

    override val width: ReadonlyLength = object : ReadonlyLength {
        override fun get() = uiDisplay.width

        override fun getMin() = uiDisplay.width
    }

    override val height: ReadonlyLength = object : ReadonlyLength {
        override fun get() = uiDisplay.height

        override fun getMin() = uiDisplay.height
    }

    override val fontSize: ReadonlyFontSize = ReadonlyFontSize { 16 }

    override val fontColour: ReadonlyFontColour = NoFontColour

    override val font: ReadonlyStyleFont = ReadonlyStyleFont { FontLoader.DEFAULT_FONT }

    override val colourModifier: ReadonlyColourModifier = NoColourModifier

    override val borders: ReadonlyStyleBorders = NoStyleBorders

    override val borderColour: ReadonlyBorderColour = NoBorderColour

    override val radius: ReadonlyRadius = NoRadius

    override val backgroundColour: ReadonlyBackgroundColour = NoBackgroundColour

    override val alignment: Alignment = Alignment(uiDisplay)
}