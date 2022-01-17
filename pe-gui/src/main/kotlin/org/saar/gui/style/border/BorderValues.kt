package org.saar.gui.style.border

import org.jproperty.constant.ConstantInteger
import org.jproperty.flatMapToInteger
import org.saar.gui.UIChildNode

object BorderValues {

    @JvmStatic
    val inherit: BorderValue = object : BorderValue {
        override fun buildTop(container: UIChildNode) =
            container.parentProperty.flatMapToInteger { it.style.borders.top }

        override fun buildRight(container: UIChildNode) =
            container.parentProperty.flatMapToInteger { it.style.borders.right }

        override fun buildBottom(container: UIChildNode) =
            container.parentProperty.flatMapToInteger { it.style.borders.bottom }

        override fun buildLeft(container: UIChildNode) =
            container.parentProperty.flatMapToInteger { it.style.borders.left }
    }

    @JvmStatic
    val none: BorderValue = object : BorderValue {
        override fun buildTop(container: UIChildNode) = ConstantInteger(0)

        override fun buildRight(container: UIChildNode) = ConstantInteger(0)

        override fun buildBottom(container: UIChildNode) = ConstantInteger(0)

        override fun buildLeft(container: UIChildNode) = ConstantInteger(0)
    }

    @JvmStatic
    fun pixels(value: Int): BorderValue = object : BorderValue {
        override fun buildTop(container: UIChildNode) = ConstantInteger(value)

        override fun buildRight(container: UIChildNode) = ConstantInteger(value)

        override fun buildBottom(container: UIChildNode) = ConstantInteger(value)

        override fun buildLeft(container: UIChildNode) = ConstantInteger(value)
    }

}