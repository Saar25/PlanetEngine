package org.saar.gui.style

import org.saar.gui.style.alignment.NoAlignment
import org.saar.gui.style.alignment.ReadonlyAlignment
import org.saar.gui.style.arrangement.NoArrangement
import org.saar.gui.style.arrangement.ReadonlyArrangement
import org.saar.gui.style.axisalignment.NoAxisAlignment
import org.saar.gui.style.axisalignment.ReadonlyAxisAlignment
import org.saar.gui.style.backgroundcolour.NoBackgroundColour
import org.saar.gui.style.backgroundcolour.ReadonlyBackgroundColour
import org.saar.gui.style.backgroundimage.NoBackgroundImage
import org.saar.gui.style.backgroundimage.ReadonlyBackgroundImage
import org.saar.gui.style.border.NoBorders
import org.saar.gui.style.border.ReadonlyBorders
import org.saar.gui.style.bordercolour.NoBorderColour
import org.saar.gui.style.bordercolour.ReadonlyBorderColour
import org.saar.gui.style.boxsizing.NoBoxSizing
import org.saar.gui.style.boxsizing.ReadonlyBoxSizing
import org.saar.gui.style.colourmodifier.NoColourModifier
import org.saar.gui.style.colourmodifier.ReadonlyColourModifier
import org.saar.gui.style.coordinate.ReadonlyCoordinate
import org.saar.gui.style.discardmap.NoDiscardMap
import org.saar.gui.style.discardmap.ReadonlyDiscardMap
import org.saar.gui.style.font.NoFontFamily
import org.saar.gui.style.font.ReadonlyFontFamily
import org.saar.gui.style.fontcolour.NoFontColour
import org.saar.gui.style.fontcolour.ReadonlyFontColour
import org.saar.gui.style.fontsize.NoFontSize
import org.saar.gui.style.fontsize.ReadonlyFontSize
import org.saar.gui.style.length.NoLength
import org.saar.gui.style.length.ReadonlyLength
import org.saar.gui.style.margin.NoMargin
import org.saar.gui.style.margin.ReadonlyMargin
import org.saar.gui.style.opacity.NoOpacity
import org.saar.gui.style.opacity.ReadonlyOpacity
import org.saar.gui.style.padding.NoPadding
import org.saar.gui.style.padding.ReadonlyPadding
import org.saar.gui.style.position.PositionValues
import org.saar.gui.style.position.ReadonlyPosition
import org.saar.gui.style.redius.NoRadius
import org.saar.gui.style.redius.ReadonlyRadius

object NoStyle : ParentStyle {

    override val position: ReadonlyPosition = object : ReadonlyPosition {
        override val value = PositionValues.absolute

        override fun getX() = 0

        override fun getY() = 0
    }

    override val margin: ReadonlyMargin = NoMargin

    override val padding: ReadonlyPadding = NoPadding

    override val x: ReadonlyCoordinate = ReadonlyCoordinate { 0 }

    override val y: ReadonlyCoordinate = ReadonlyCoordinate { 0 }

    override val width: ReadonlyLength = NoLength

    override val height: ReadonlyLength = NoLength

    override val boxSizing: ReadonlyBoxSizing = NoBoxSizing

    override val fontSize: ReadonlyFontSize = NoFontSize

    override val fontColour: ReadonlyFontColour = NoFontColour

    override val font: ReadonlyFontFamily = NoFontFamily

    override val colourModifier: ReadonlyColourModifier = NoColourModifier

    override val borders: ReadonlyBorders = NoBorders

    override val borderColour: ReadonlyBorderColour = NoBorderColour

    override val radius: ReadonlyRadius = NoRadius

    override val opacity: ReadonlyOpacity = NoOpacity

    override val backgroundColour: ReadonlyBackgroundColour = NoBackgroundColour

    override val backgroundImage: ReadonlyBackgroundImage = NoBackgroundImage

    override val discardMap: ReadonlyDiscardMap = NoDiscardMap

    override val alignment: ReadonlyAlignment = NoAlignment

    override val arrangement: ReadonlyArrangement = NoArrangement

    override val axisAlignment: ReadonlyAxisAlignment = NoAxisAlignment
}