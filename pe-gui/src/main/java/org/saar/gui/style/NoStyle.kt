package org.saar.gui.style

import org.saar.gui.style.backgroundcolour.NoBackgroundColour
import org.saar.gui.style.backgroundcolour.ReadonlyBackgroundColour
import org.saar.gui.style.border.NoStyleBorders
import org.saar.gui.style.border.ReadonlyStyleBorders
import org.saar.gui.style.bordercolour.NoBorderColour
import org.saar.gui.style.bordercolour.ReadonlyBorderColour
import org.saar.gui.style.colourmodifier.NoColourModifier
import org.saar.gui.style.colourmodifier.ReadonlyColourModifier
import org.saar.gui.style.coordinate.ReadonlyCoordinate
import org.saar.gui.style.length.ReadonlyLength
import org.saar.gui.style.redius.NoStyleRadiuses
import org.saar.gui.style.redius.ReadonlyStyleRadiuses

object NoStyle : IStyle {

    override val x: ReadonlyCoordinate = ReadonlyCoordinate { 0 }

    override val y: ReadonlyCoordinate = ReadonlyCoordinate { 0 }

    override val width: ReadonlyLength = ReadonlyLength { 0 }

    override val height: ReadonlyLength = ReadonlyLength { 0 }

    override val colourModifier: ReadonlyColourModifier = NoColourModifier

    override val borders: ReadonlyStyleBorders = NoStyleBorders

    override val borderColour: ReadonlyBorderColour = NoBorderColour

    override val radiuses: ReadonlyStyleRadiuses = NoStyleRadiuses

    override val backgroundColour: ReadonlyBackgroundColour = NoBackgroundColour

}