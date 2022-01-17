package org.saar.gui.style.margin

import org.jproperty.constant.ConstantIntegerProperty
import org.jproperty.mapToInteger
import org.saar.gui.UIChildNode

object MarginValues {

    @JvmStatic
    val inherit: MarginValue = object : MarginValue {
        override fun buildTop(container: UIChildNode) =
            container.parentProperty.mapToInteger { it.style.margin.top.get() }

        override fun buildRight(container: UIChildNode) =
            container.parentProperty.mapToInteger { it.style.margin.right.get() }

        override fun buildBottom(container: UIChildNode) =
            container.parentProperty.mapToInteger { it.style.margin.bottom.get() }

        override fun buildLeft(container: UIChildNode) =
            container.parentProperty.mapToInteger { it.style.margin.left.get() }
    }

    @JvmStatic
    val none: MarginValue = object : MarginValue {
        override fun buildTop(container: UIChildNode) = ConstantIntegerProperty(0)

        override fun buildRight(container: UIChildNode) = ConstantIntegerProperty(0)

        override fun buildBottom(container: UIChildNode) = ConstantIntegerProperty(0)

        override fun buildLeft(container: UIChildNode) = ConstantIntegerProperty(0)
    }

    @JvmStatic
    fun pixels(value: Int): MarginValue = object : MarginValue {
        override fun buildTop(container: UIChildNode) = ConstantIntegerProperty(value)

        override fun buildRight(container: UIChildNode) = ConstantIntegerProperty(value)

        override fun buildBottom(container: UIChildNode) = ConstantIntegerProperty(value)

        override fun buildLeft(container: UIChildNode) = ConstantIntegerProperty(value)
    }

}