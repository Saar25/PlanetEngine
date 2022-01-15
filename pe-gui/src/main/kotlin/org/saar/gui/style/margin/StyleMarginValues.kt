package org.saar.gui.style.margin

import org.jproperty.constant.ConstantIntegerProperty
import org.saar.gui.UIChildNode

object StyleMarginValues {

    @JvmStatic
    val inherit: StyleMarginValue = object : StyleMarginValue {
        override fun buildTop(container: UIChildNode) = container.parent.style.margin.top

        override fun buildRight(container: UIChildNode) = container.parent.style.margin.right

        override fun buildBottom(container: UIChildNode) = container.parent.style.margin.bottom

        override fun buildLeft(container: UIChildNode) = container.parent.style.margin.left
    }

    @JvmStatic
    val none: StyleMarginValue = object : StyleMarginValue {
        override fun buildTop(container: UIChildNode) = ConstantIntegerProperty(0)

        override fun buildRight(container: UIChildNode) = ConstantIntegerProperty(0)

        override fun buildBottom(container: UIChildNode) = ConstantIntegerProperty(0)

        override fun buildLeft(container: UIChildNode) = ConstantIntegerProperty(0)
    }

    @JvmStatic
    fun pixels(value: Int): StyleMarginValue = object : StyleMarginValue {
        override fun buildTop(container: UIChildNode) = ConstantIntegerProperty(value)

        override fun buildRight(container: UIChildNode) = ConstantIntegerProperty(value)

        override fun buildBottom(container: UIChildNode) = ConstantIntegerProperty(value)

        override fun buildLeft(container: UIChildNode) = ConstantIntegerProperty(value)
    }

}