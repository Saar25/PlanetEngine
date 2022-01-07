package org.saar.gui.style.value

import org.saar.gui.UINode
import org.saar.gui.UIParentNode

object AlignmentValues {

    @JvmStatic
    fun horizontal() = object : AlignmentValue {
        override fun computeAxisX(parent: UIParentNode, child: UINode): Int {
            return parent.children.takeWhile { it != child }.sumOf { it.style.width.get() }
        }

        override fun computeAxisY(parent: UIParentNode, child: UINode): Int {
            return 0
        }
    }

    @JvmStatic
    fun vertical() = object : AlignmentValue {
        override fun computeAxisX(parent: UIParentNode, child: UINode): Int {
            return 0
        }

        override fun computeAxisY(parent: UIParentNode, child: UINode): Int {
            return parent.children.takeWhile { it != child }.sumOf { it.style.height.get() }
        }
    }
}