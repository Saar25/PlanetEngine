package org.saar.gui.style.backgroundcolour

import org.saar.gui.UIChildNode
import org.saar.gui.style.Colour

object BackgroundColourValues {

    @JvmField
    val inherit: BackgroundColourValue = object : BackgroundColourValue {
        override fun computeTopRight(container: UIChildNode) = container.parent.style.backgroundColour.topRight

        override fun computeTopLeft(container: UIChildNode) = container.parent.style.backgroundColour.topLeft

        override fun computeBottomRight(container: UIChildNode) = container.parent.style.backgroundColour.bottomRight

        override fun computeBottomLeft(container: UIChildNode) = container.parent.style.backgroundColour.bottomLeft
    }

    @JvmStatic
    fun of(value: Colour): BackgroundColourValue = object : BackgroundColourValue {
        override fun computeTopRight(container: UIChildNode) = value

        override fun computeTopLeft(container: UIChildNode) = value

        override fun computeBottomRight(container: UIChildNode) = value

        override fun computeBottomLeft(container: UIChildNode) = value
    }

}