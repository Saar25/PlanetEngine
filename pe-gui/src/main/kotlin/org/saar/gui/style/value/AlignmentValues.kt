package org.saar.gui.style.value

import org.saar.gui.UIChildNode
import org.saar.gui.UIParentNode

object AlignmentValues {

    @JvmStatic
    val horizontal = object : AlignmentValue {
        override fun computeAxisX(parent: UIParentNode, child: UIChildNode): Int {
            return parent.children.takeWhile { it != child }.sumOf {
                it.style.width.get() +
                        it.style.borders.left + it.style.borders.right +
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
                        it.style.borders.top + it.style.borders.bottom +
                        it.style.margin.top + it.style.margin.bottom
            }
        }
    }
}