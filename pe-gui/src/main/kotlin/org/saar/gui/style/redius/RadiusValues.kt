package org.saar.gui.style.redius

import org.jproperty.constant.ConstantInteger
import org.jproperty.mapToInteger
import org.saar.gui.UIChildNode

object RadiusValues {

    @JvmStatic
    val inherit: RadiusValue = object : RadiusValue {
        override fun buildTopRight(container: UIChildNode) =
            container.parentProperty.mapToInteger { it.style.radius.topRight.get() }

        override fun buildTopLeft(container: UIChildNode) =
            container.parentProperty.mapToInteger { it.style.radius.topLeft.get() }

        override fun buildBottomRight(container: UIChildNode) =
            container.parentProperty.mapToInteger { it.style.radius.bottomRight.get() }

        override fun buildBottomLeft(container: UIChildNode) =
            container.parentProperty.mapToInteger { it.style.radius.bottomLeft.get() }
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