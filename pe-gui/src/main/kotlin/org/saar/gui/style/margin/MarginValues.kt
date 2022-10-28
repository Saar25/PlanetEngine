package org.saar.gui.style.margin

import org.saar.gui.UIChildNode

object MarginValues {

    @JvmField
    val inherit: MarginValue = object : MarginValue {
        override fun computeTop(container: UIChildNode) = container.parent.style.margin.top

        override fun computeRight(container: UIChildNode) = container.parent.style.margin.right

        override fun computeBottom(container: UIChildNode) = container.parent.style.margin.bottom

        override fun computeLeft(container: UIChildNode) = container.parent.style.margin.left
    }

    @JvmField
    val none: MarginValue = object : MarginValue {
        override fun computeTop(container: UIChildNode) = 0

        override fun computeRight(container: UIChildNode) = 0

        override fun computeBottom(container: UIChildNode) = 0

        override fun computeLeft(container: UIChildNode) = 0
    }

    @JvmStatic
    fun pixels(value: Int): MarginValue = object : MarginValue {
        override fun computeTop(container: UIChildNode) = value

        override fun computeRight(container: UIChildNode) = value

        override fun computeBottom(container: UIChildNode) = value

        override fun computeLeft(container: UIChildNode) = value
    }

}