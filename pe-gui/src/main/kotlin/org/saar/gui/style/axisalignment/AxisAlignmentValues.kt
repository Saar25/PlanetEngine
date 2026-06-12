package org.saar.gui.style.axisalignment

import org.saar.gui.UIChildNode
import org.saar.gui.UIParentNode

object AxisAlignmentValues {

    @JvmField
    val none = object : AxisAlignmentValue {
        override fun computeAxisX(container: UIParentNode, child: UIChildNode) = 0

        override fun computeAxisY(container: UIParentNode, child: UIChildNode) = 0
    }

    @JvmField
    val start = object : AxisAlignmentValue {
        override fun computeAxisX(container: UIParentNode, child: UIChildNode) = 0

        override fun computeAxisY(container: UIParentNode, child: UIChildNode) = 0
    }

    @JvmField
    val end = object : AxisAlignmentValue {
        override fun computeAxisX(container: UIParentNode, child: UIChildNode) =
            container.style.width.get() - child.style.width.get()

        override fun computeAxisY(container: UIParentNode, child: UIChildNode) =
            container.style.height.get() - child.style.height.get()
    }

    @JvmField
    val center = object : AxisAlignmentValue {
        override fun computeAxisX(container: UIParentNode, child: UIChildNode): Int =
            (container.style.boxSizing.getSpaceWidth() - child.style.boxSizing.getSpaceWidth()) / 2

        override fun computeAxisY(container: UIParentNode, child: UIChildNode): Int =
            (container.style.boxSizing.getBoxHeight() - child.style.boxSizing.getBoxHeight()) / 2
    }
}