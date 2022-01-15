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
                        it.style.borders.left.get() + it.style.borders.right.get() +
                        it.style.margin.left.get() + it.style.margin.right.get()
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
                        it.style.borders.top.get() + it.style.borders.bottom.get() +
                        it.style.margin.top.get() + it.style.margin.bottom.get()
            }
        }
    }
}