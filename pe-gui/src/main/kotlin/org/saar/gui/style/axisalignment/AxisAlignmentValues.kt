package org.saar.gui.style.axisalignment

import org.saar.gui.UIChildNode
import org.saar.gui.UIParentNode

object AxisAlignmentValues {

    @JvmStatic
    val none = object : AxisAlignmentValue {
        override fun computeAxisX(container: UIParentNode, child: UIChildNode) = 0

        override fun computeAxisY(container: UIParentNode, child: UIChildNode) = 0
    }

    @JvmStatic
    val start = object : AxisAlignmentValue {
        override fun computeAxisX(container: UIParentNode, child: UIChildNode) = 0

        override fun computeAxisY(container: UIParentNode, child: UIChildNode) = 0
    }

    @JvmStatic
    val end = object : AxisAlignmentValue {
        override fun computeAxisX(container: UIParentNode, child: UIChildNode) =
            container.style.width.get() - child.style.width.get()

        override fun computeAxisY(container: UIParentNode, child: UIChildNode) =
            container.style.height.get() - child.style.height.get()
    }

    @JvmStatic
    val center = object : AxisAlignmentValue {
        override fun computeAxisX(container: UIParentNode, child: UIChildNode): Int =
            (container.style.width.get() - child.style.width.get()) / 2

        override fun computeAxisY(container: UIParentNode, child: UIChildNode): Int =
            (container.style.height.get() - child.style.height.get()) / 2
    }
}