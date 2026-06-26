package org.saar.gui.style.backgroundcolor

import org.saar.gui.UIChildNode
import org.saar.gui.style.Color

interface BackgroundColorValue {

    fun computeTopRight(container: UIChildNode): Color

    fun computeTopLeft(container: UIChildNode): Color

    fun computeBottomRight(container: UIChildNode): Color

    fun computeBottomLeft(container: UIChildNode): Color

}