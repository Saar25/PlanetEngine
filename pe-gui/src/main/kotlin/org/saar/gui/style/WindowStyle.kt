package org.saar.gui.style

import org.saar.gui.UIChildElement
import org.saar.gui.font.FontLoader
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
import org.saar.gui.style.font.ReadonlyStyleFont
import org.saar.gui.style.fontcolour.NoFontColour
import org.saar.gui.style.fontcolour.ReadonlyFontColour
import org.saar.gui.style.fontsize.ReadonlyFontSize
import org.saar.gui.style.length.ReadonlyLength
import org.saar.gui.style.redius.NoStyleRadiuses
import org.saar.gui.style.redius.ReadonlyStyleRadiuses
import org.saar.lwjgl.glfw.window.Window

class WindowStyle(private val window: Window) : ParentStyle {

    override val x: ReadonlyCoordinate = ReadonlyCoordinate { 0 }

    override val y: ReadonlyCoordinate = ReadonlyCoordinate { 0 }

    override val width: ReadonlyLength = ReadonlyLength { this.window.width }

    override val height: ReadonlyLength = ReadonlyLength { this.window.height }

    override val fontSize: ReadonlyFontSize = ReadonlyFontSize { 16 }

    override val fontColour: ReadonlyFontColour = NoFontColour

    override val font: ReadonlyStyleFont = ReadonlyStyleFont { FontLoader.DEFAULT_FONT }

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