package org.saar.gui.style.backgroundcolour

import org.saar.gui.UIChildNode
import org.saar.gui.style.Colour

interface BackgroundColourValue {
    fun computeTopRight(container: UIChildNode): Colour

    fun computeTopLeft(container: UIChildNode): Colour

    fun computeBottomRight(container: UIChildNode): Colour

    fun computeBottomLeft(container: UIChildNode): Colour
}