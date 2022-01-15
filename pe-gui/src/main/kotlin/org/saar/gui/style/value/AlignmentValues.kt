package org.saar.gui.style.value

import org.saar.gui.UIChildNode
import org.saar.gui.UIParentNode

object AlignmentValues {

    @JvmStatic
    val none = object : AlignmentValue {
        override fun computeAxisX(parent: UIParentNode, child: UIChildNode) = 0

        override fun computeAxisY(parent: UIParentNode, child: UIChildNode) = 0
    }

    @JvmStatic
    val horizontal = object : AlignmentValue {
        override fun computeAxisX(parent: UIParentNode, child: UIChildNode): Int {
            return parent.children.takeWhile { it != child }.sumOf {
                it.style.width.get() +
                        it.style.borders.left.get() + it.style.borders.right.get() +
                        it.style.margin.left + it.style.margin.right
            }
        }

        override fun computeAxisY(parent: UIParentNode, child: UIChildNode): Int {
            return 0
        }
    }

    @JvmStatic
    val vertical = object : AlignmentValue {
        override fun computeAxisX(parent: UIParentNode, child: UIChildNode): Int {
            return 0
        }

        override fun computeAxisY(parent: UIParentNode, child: UIChildNode): Int {
            return parent.children.takeWhile { it != child }.sumOf {
                it.style.height.get() +
                        it.style.borders.top.get() + it.style.borders.bottom.get() +
                        it.style.margin.top + it.style.margin.bottom
            }
        }
    }
}