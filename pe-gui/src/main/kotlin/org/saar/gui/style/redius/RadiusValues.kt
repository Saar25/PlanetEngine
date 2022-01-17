package org.saar.gui.style.redius

import org.jproperty.constant.ConstantInteger
import org.jproperty.flatMapToInteger
import org.saar.gui.UIChildNode

object RadiusValues {

    @JvmStatic
    val inherit: RadiusValue = object : RadiusValue {
        override fun buildTopRight(container: UIChildNode) =
            container.parentProperty.flatMapToInteger { it.style.radius.topRight }

        override fun buildTopLeft(container: UIChildNode) =
            container.parentProperty.flatMapToInteger { it.style.radius.topLeft }

        override fun buildBottomRight(container: UIChildNode) =
            container.parentProperty.flatMapToInteger { it.style.radius.bottomRight }

        override fun buildBottomLeft(container: UIChildNode) =
            container.parentProperty.flatMapToInteger { it.style.radius.bottomLeft }
    }

    @JvmStatic
    val none: RadiusValue = object : RadiusValue {
        override fun buildTopRight(container: UIChildNode) = ConstantInteger(0)

        override fun buildTopLeft(container: UIChildNode) = ConstantInteger(0)

        override fun buildBottomRight(container: UIChildNode) = ConstantInteger(0)

        override fun buildBottomLeft(container: UIChildNode) = ConstantInteger(0)
    }

    @JvmStatic
    fun pixels(value: Int): RadiusValue = object : RadiusValue {
        override fun buildTopRight(container: UIChildNode) = ConstantInteger(value)

        override fun buildTopLeft(container: UIChildNode) = ConstantInteger(value)

        override fun buildBottomRight(container: UIChildNode) = ConstantInteger(value)

        override fun buildBottomLeft(container: UIChildNode) = ConstantInteger(value)
    }

}