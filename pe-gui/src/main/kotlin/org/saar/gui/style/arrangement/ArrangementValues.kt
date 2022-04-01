package org.saar.gui.style.arrangement

import org.saar.gui.UIChildNode
import org.saar.gui.UINode
import org.saar.gui.UIParentNode
import org.saar.gui.style.position.PositionValues

object ArrangementValues {

    @JvmField
    val none = object : ArrangementValue {
        override fun computeAxisX(container: UIParentNode, child: UIChildNode) = 0

        override fun computeAxisY(container: UIParentNode, child: UIChildNode) = 0
    }

    private fun Iterable<UINode>.onlyRelative() = filter { it.style.position.value == PositionValues.relative }

    private fun Iterable<UINode>.totalWidth() =
        onlyRelative().sumOf {
            it.style.width.get() +
                    it.style.borders.left + it.style.borders.right +
                    it.style.margin.left + it.style.margin.right
        }

    private fun Iterable<UINode>.totalHeight() =
        onlyRelative().sumOf {
            it.style.height.get() +
                    it.style.borders.top + it.style.borders.bottom +
                    it.style.margin.top + it.style.margin.bottom
        }

    @JvmField
    val start = object : ArrangementValue {
        override fun computeAxisX(container: UIParentNode, child: UIChildNode): Int {
            return container.children.takeWhile { it != child }.totalWidth()
        }

        override fun computeAxisY(container: UIParentNode, child: UIChildNode): Int {
            return container.children.takeWhile { it != child }.totalHeight()
        }
    }

    @JvmField
    val end = object : ArrangementValue {
        override fun computeAxisX(container: UIParentNode, child: UIChildNode): Int {
            return container.style.width.get() - child.style.width.get() -
                    container.children.takeLastWhile { it != child }.totalWidth()
        }

        override fun computeAxisY(container: UIParentNode, child: UIChildNode): Int {
            return container.style.height.get() - child.style.height.get() -
                    container.children.takeLastWhile { it != child }.totalHeight()
        }
    }

    @JvmField
    val center = object : ArrangementValue {
        override fun computeAxisX(container: UIParentNode, child: UIChildNode): Int {
            val children = container.children.totalWidth()
            val start = container.children.takeWhile { it != child }.totalWidth()
            return (container.style.width.get() - children) / 2 + start
        }

        override fun computeAxisY(container: UIParentNode, child: UIChildNode): Int {
            val children = container.children.totalHeight()
            val start = container.children.takeWhile { it != child }.totalHeight()
            return (container.style.height.get() - children) / 2 + start
        }
    }

    @JvmField
    val spaceBetween = object : ArrangementValue {
        override fun computeAxisX(container: UIParentNode, child: UIChildNode): Int {
            val total = container.style.width.get()
            val children = container.children.totalWidth()
            val gap = (total - children) / (container.children.size - 1)

            val before = container.children.takeWhile { it != child }
            return gap * before.size + before.totalWidth()
        }

        override fun computeAxisY(container: UIParentNode, child: UIChildNode): Int {
            val total = container.style.height.get()
            val children = container.children.totalHeight()
            val gap = (total - children) / (container.children.size - 1)

            val before = container.children.takeWhile { it != child }
            return gap * before.size + before.totalHeight()
        }
    }

    @JvmField
    val spaceAround = object : ArrangementValue {
        override fun computeAxisX(container: UIParentNode, child: UIChildNode): Int {
            val total = container.style.width.get()
            val children = container.children.totalWidth()
            val gap = (total - children) / (container.children.size * 2)

            val before = container.children.takeWhile { it != child }
            return gap + 2 * gap * before.size + before.totalWidth()
        }

        override fun computeAxisY(container: UIParentNode, child: UIChildNode): Int {
            val total = container.style.height.get()
            val children = container.children.totalHeight()
            val gap = (total - children) / (container.children.size * 2)

            val before = container.children.takeWhile { it != child }
            return gap + 2 * gap * before.size + before.totalHeight()
        }
    }
}