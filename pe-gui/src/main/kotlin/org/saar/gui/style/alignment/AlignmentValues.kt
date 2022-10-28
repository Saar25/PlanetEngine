package org.saar.gui.style.alignment

import org.saar.gui.UIChildNode
import org.saar.gui.UIParentNode

object AlignmentValues {

    @JvmField
    val none = object : AlignmentValue {
        override fun computeAxisX(container: UIParentNode, child: UIChildNode) = 0

        override fun computeAxisY(container: UIParentNode, child: UIChildNode) = 0
    }

    @JvmField
    val horizontal = object : AlignmentValue {
        override fun computeAxisX(container: UIParentNode, child: UIChildNode) =
            container.style.arrangement.getX(child)

        override fun computeAxisY(container: UIParentNode, child: UIChildNode) =
            container.style.axisAlignment.getY(child)
    }

    @JvmField
    val vertical = object : AlignmentValue {
        override fun computeAxisX(container: UIParentNode, child: UIChildNode) =
            container.style.axisAlignment.getX(child)

        override fun computeAxisY(container: UIParentNode, child: UIChildNode) =
            container.style.arrangement.getY(child)
    }
}