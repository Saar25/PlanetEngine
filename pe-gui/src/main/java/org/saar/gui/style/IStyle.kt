package org.saar.gui.style

import org.joml.Vector4fc
import org.saar.gui.style.backgroundcolour.ReadonlyBackgroundColour
import org.saar.gui.style.border.ReadonlyStyleBorders
import org.saar.gui.style.bordercolour.ReadonlyBorderColour
import org.saar.gui.style.colourmodifier.ReadonlyColourModifier
import org.saar.gui.style.coordinate.ReadonlyCoordinate
import org.saar.gui.style.length.ReadonlyLength
import org.saar.gui.style.redius.ReadonlyStyleRadiuses

interface IStyle {

    val x: ReadonlyCoordinate

    val y: ReadonlyCoordinate

    val width: ReadonlyLength

    val height: ReadonlyLength

    val colourModifier: ReadonlyColourModifier

    val borders: ReadonlyStyleBorders

    val borderColour: ReadonlyBorderColour

    val radiuses: ReadonlyStyleRadiuses

    val backgroundColour: ReadonlyBackgroundColour

}