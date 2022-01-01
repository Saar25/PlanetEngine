package org.saar.gui.style.value

import org.saar.gui.UIChildElement
import org.saar.gui.UIContainer

object ItemsAlignmentValues {

    @JvmStatic
    fun spaceBetween() = object : ItemsAlignmentValue {
        override fun computeAxisX(parent: UIContainer, child: UIChildElement): Int {
            val width = parent.children.sumOf { it.style.width.get() }
            val gap = (parent.style.width.get() - width) / (parent.children.size - 1)
            val before = parent.children.takeWhile { it != child }

            return before.sumOf { it.style.width.get() } + gap * before.size
        }

        override fun computeAxisY(parent: UIContainer, child: UIChildElement): Int {
            val height = parent.children.sumOf { it.style.height.get() }
            val gap = (parent.style.height.get() - height) / (parent.children.size - 1)
            val before = parent.children.takeWhile { it != child }

            return before.sumOf { it.style.height.get() } + gap * before.size
        }
    }
}