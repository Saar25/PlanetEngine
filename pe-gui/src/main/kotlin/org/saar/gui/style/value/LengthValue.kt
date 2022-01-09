package org.saar.gui.style.value

import org.saar.gui.UIChildNode
import org.saar.gui.UIParentNode

interface LengthValue {
    fun computeAxisX(parent: UIParentNode, container: UIChildNode): Int

    fun computeAxisY(parent: UIParentNode, container: UIChildNode): Int

    fun interface Simple : LengthValue {
        fun compute(parent: UIParentNode, container: UIChildNode): Int

        override fun computeAxisX(parent: UIParentNode, container: UIChildNode) = compute(parent, container)
        override fun computeAxisY(parent: UIParentNode, container: UIChildNode) = compute(parent, container)
    }
}