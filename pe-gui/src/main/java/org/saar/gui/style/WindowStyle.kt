package org.saar.gui.style

import org.joml.Vector4fc
import org.saar.gui.style.border.ReadonlyStyleBorders
import org.saar.gui.style.border.WindowStyleBorders
import org.saar.gui.style.bordercolours.BordersColours
import org.saar.gui.style.bordercolours.ReadonlyBordersColours
import org.saar.gui.style.property.CornersColours
import org.saar.gui.style.redius.ReadonlyStyleRadiuses
import org.saar.gui.style.redius.WindowStyleRadiuses
import org.saar.maths.utils.Vector4

object WindowStyle : IStyle {

    override val colourModifier: Vector4fc = Vector4.of(1f)

    override val borders: ReadonlyStyleBorders = WindowStyleBorders

    override val borderColour: ReadonlyBordersColours = BordersColours(null)

    override val radiuses: ReadonlyStyleRadiuses = WindowStyleRadiuses

    override val backgroundColour: CornersColours = CornersColours()

}