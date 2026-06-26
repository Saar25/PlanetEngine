package org.saar.gui.style.backgroundcolor

import org.saar.gui.UIChildNode
import org.saar.gui.style.Color

object BackgroundColorValues {

    @JvmField
    val inherit: BackgroundColorValue = object : BackgroundColorValue {
        override fun computeTopRight(container: UIChildNode) = container.parent.style.backgroundColor.topRight

        override fun computeTopLeft(container: UIChildNode) = container.parent.style.backgroundColor.topLeft

        override fun computeBottomRight(container: UIChildNode) = container.parent.style.backgroundColor.bottomRight

        override fun computeBottomLeft(container: UIChildNode) = container.parent.style.backgroundColor.bottomLeft
    }

    @JvmStatic
    fun of(value: Color): BackgroundColorValue = object : BackgroundColorValue {
        override fun computeTopRight(container: UIChildNode) = value

        override fun computeTopLeft(container: UIChildNode) = value

        override fun computeBottomRight(container: UIChildNode) = value

        override fun computeBottomLeft(container: UIChildNode) = value
    }

}