package org.saar.gui.style.alignment

import org.saar.gui.UIChildNode
import org.saar.gui.UIParentNode

object AlignmentValues {

    @JvmStatic
    val none = object : AlignmentValue {
        override fun computeAxisX(container: UIParentNode, child: UIChildNode) = 0

        override fun computeAxisY(container: UIParentNode, child: UIChildNode) = 0
    }

    @JvmStatic
    val horizontal = object : AlignmentValue {
        override fun computeAxisX(container: UIParentNode, child: UIChildNode): Int {
            return container.children.takeWhile { it != child }.sumOf {
                it.style.width.get() +
                        it.style.borders.left + it.style.borders.right +
                        it.style.margin.left + it.style.margin.right
            }
        }

        override fun computeAxisY(container: UIParentNode, child: UIChildNode) = 0
    }

    @JvmStatic
    val vertical = object : AlignmentValue {
        override fun computeAxisX(container: UIParentNode, child: UIChildNode) = 0

        override fun computeAxisY(container: UIParentNode, child: UIChildNode): Int {
            return container.children.takeWhile { it != child }.sumOf {
                it.style.height.get() +
                        it.style.borders.top + it.style.borders.bottom +
                        it.style.margin.top + it.style.margin.bottom
            }
        }
    }
}