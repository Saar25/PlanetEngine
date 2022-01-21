package org.saar.gui.style.border

import org.saar.gui.UIChildNode

object BorderValues {

    @JvmStatic
    val inherit: BorderValue = object : BorderValue {
        override fun computeTop(container: UIChildNode) = container.parent.style.borders.top

        override fun computeRight(container: UIChildNode) = container.parent.style.borders.right

        override fun computeBottom(container: UIChildNode) = container.parent.style.borders.bottom

        override fun computeLeft(container: UIChildNode) = container.parent.style.borders.left
    }

    @JvmStatic
    val none: BorderValue = object : BorderValue {
        override fun computeTop(container: UIChildNode) = 0

        override fun computeRight(container: UIChildNode) = 0

        override fun computeBottom(container: UIChildNode) = 0

        override fun computeLeft(container: UIChildNode) = 0
    }

    @JvmStatic
    fun pixels(value: Int): BorderValue = object : BorderValue {
        override fun computeTop(container: UIChildNode) = value

        override fun computeRight(container: UIChildNode) = value

        override fun computeBottom(container: UIChildNode) = value

        override fun computeLeft(container: UIChildNode) = value
    }
}