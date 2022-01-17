package org.saar.gui.style.margin

import org.jproperty.constant.ConstantInteger
import org.jproperty.flatMapToInteger
import org.saar.gui.UIChildNode

object MarginValues {

    @JvmStatic
    val inherit: MarginValue = object : MarginValue {
        override fun buildTop(container: UIChildNode) =
            container.parentProperty.flatMapToInteger { it.style.margin.top }

        override fun buildRight(container: UIChildNode) =
            container.parentProperty.flatMapToInteger { it.style.margin.right }

        override fun buildBottom(container: UIChildNode) =
            container.parentProperty.flatMapToInteger { it.style.margin.bottom }

        override fun buildLeft(container: UIChildNode) =
            container.parentProperty.flatMapToInteger { it.style.margin.left }
    }

    @JvmStatic
    val none: MarginValue = object : MarginValue {
        override fun buildTop(container: UIChildNode) = ConstantInteger(0)

        override fun buildRight(container: UIChildNode) = ConstantInteger(0)

        override fun buildBottom(container: UIChildNode) = ConstantInteger(0)

        override fun buildLeft(container: UIChildNode) = ConstantInteger(0)
    }

    @JvmStatic
    fun pixels(value: Int): MarginValue = object : MarginValue {
        override fun buildTop(container: UIChildNode) = ConstantInteger(value)

        override fun buildRight(container: UIChildNode) = ConstantInteger(value)

        override fun buildBottom(container: UIChildNode) = ConstantInteger(value)

        override fun buildLeft(container: UIChildNode) = ConstantInteger(value)
    }

}