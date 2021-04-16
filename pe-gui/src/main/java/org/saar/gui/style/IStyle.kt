package org.saar.gui.style

import org.joml.Vector4fc
import org.saar.gui.style.border.ReadonlyStyleBorders
import org.saar.gui.style.bordercolours.ReadonlyBordersColours
import org.saar.gui.style.property.CornersColours
import org.saar.gui.style.redius.ReadonlyStyleRadiuses

interface IStyle {

    val colourModifier: Vector4fc

    val borders: ReadonlyStyleBorders

    val borderColour: ReadonlyBordersColours

    val radiuses: ReadonlyStyleRadiuses

    val backgroundColour: CornersColours

}