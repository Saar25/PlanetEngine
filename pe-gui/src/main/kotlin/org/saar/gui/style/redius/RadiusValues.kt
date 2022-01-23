package org.saar.gui.style.redius

import org.saar.gui.UIChildNode

object RadiusValues {

    @JvmStatic
    val inherit: RadiusValue = object : RadiusValue {
        override fun computeTopRight(container: UIChildNode) = container.parent.style.radius.topRight

        override fun computeTopLeft(container: UIChildNode) = container.parent.style.radius.topLeft

        override fun computeBottomRight(container: UIChildNode) = container.parent.style.radius.bottomRight

        override fun computeBottomLeft(container: UIChildNode) = container.parent.style.radius.bottomLeft
    }

    @JvmStatic
    val none: RadiusValue = object : RadiusValue {
        override fun computeTopRight(container: UIChildNode) = 0

        override fun computeTopLeft(container: UIChildNode) = 0

        override fun computeBottomRight(container: UIChildNode) = 0

        override fun computeBottomLeft(container: UIChildNode) = 0
    }

    @JvmStatic
    fun pixels(value: Int): RadiusValue = object : RadiusValue {
        override fun computeTopRight(container: UIChildNode) = value

        override fun computeTopLeft(container: UIChildNode) = value

        override fun computeBottomRight(container: UIChildNode) = value

        override fun computeBottomLeft(container: UIChildNode) = value
    }

}