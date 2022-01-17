package org.saar.gui.style.border

import org.jproperty.constant.ConstantIntegerProperty
import org.saar.gui.UIChildNode

object BorderValues {

    @JvmStatic
    val inherit: BorderValue = object : BorderValue {
        override fun buildTop(container: UIChildNode) = container.parent.style.borders.top

        override fun buildRight(container: UIChildNode) = container.parent.style.borders.right

        override fun buildBottom(container: UIChildNode) = container.parent.style.borders.bottom

        override fun buildLeft(container: UIChildNode) = container.parent.style.borders.left
    }

    @JvmStatic
    val none: BorderValue = object : BorderValue {
        override fun buildTop(container: UIChildNode) = ConstantIntegerProperty(0)

        override fun buildRight(container: UIChildNode) = ConstantIntegerProperty(0)

        override fun buildBottom(container: UIChildNode) = ConstantIntegerProperty(0)

        override fun buildLeft(container: UIChildNode) = ConstantIntegerProperty(0)
    }

    @JvmStatic
    fun pixels(value: Int): BorderValue = object : BorderValue {
        override fun buildTop(container: UIChildNode) = ConstantIntegerProperty(value)

        override fun buildRight(container: UIChildNode) = ConstantIntegerProperty(value)

        override fun buildBottom(container: UIChildNode) = ConstantIntegerProperty(value)

        override fun buildLeft(container: UIChildNode) = ConstantIntegerProperty(value)
    }

}