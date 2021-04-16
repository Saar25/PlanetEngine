package org.saar.gui.style

import org.joml.Vector4fc
import org.saar.gui.style.backgroundcolour.ReadonlyBackgroundColour
import org.saar.gui.style.backgroundcolour.WindowBackgroundColour
import org.saar.gui.style.border.ReadonlyStyleBorders
import org.saar.gui.style.border.WindowStyleBorders
import org.saar.gui.style.bordercolour.ReadonlyBorderColour
import org.saar.gui.style.bordercolour.WindowBorderColour
import org.saar.gui.style.redius.ReadonlyStyleRadiuses
import org.saar.gui.style.redius.WindowStyleRadiuses
import org.saar.maths.utils.Vector4

object WindowStyle : IStyle {

    override val colourModifier: Vector4fc = Vector4.of(1f)

    override val borders: ReadonlyStyleBorders = WindowStyleBorders

    override val borderColour: ReadonlyBorderColour = WindowBorderColour

    override val radiuses: ReadonlyStyleRadiuses = WindowStyleRadiuses

    override val backgroundColour: ReadonlyBackgroundColour = WindowBackgroundColour

}