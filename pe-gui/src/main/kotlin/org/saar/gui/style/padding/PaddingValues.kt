package org.saar.gui.style.padding

import org.saar.gui.UIChildNode

object PaddingValues {

    @JvmField
    val inherit: PaddingValue = object : PaddingValue {
        override fun computeTop(container: UIChildNode) = container.parent.style.padding.top

        override fun computeRight(container: UIChildNode) = container.parent.style.padding.right

        override fun computeBottom(container: UIChildNode) = container.parent.style.padding.bottom

        override fun computeLeft(container: UIChildNode) = container.parent.style.padding.left
    }

    @JvmField
    val none: PaddingValue = object : PaddingValue {
        override fun computeTop(container: UIChildNode) = 0

        override fun computeRight(container: UIChildNode) = 0

        override fun computeBottom(container: UIChildNode) = 0

        override fun computeLeft(container: UIChildNode) = 0
    }

    @JvmStatic
    fun pixels(value: Int): PaddingValue = object : PaddingValue {
        override fun computeTop(container: UIChildNode) = value

        override fun computeRight(container: UIChildNode) = value

        override fun computeBottom(container: UIChildNode) = value

        override fun computeLeft(container: UIChildNode) = value
    }

}