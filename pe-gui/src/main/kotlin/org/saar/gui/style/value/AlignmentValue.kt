package org.saar.gui.style.value

import org.saar.gui.UIChildNode
import org.saar.gui.UIParentNode

interface AlignmentValue {
    fun computeAxisX(parent: UIParentNode, child: UIChildNode): Int

    fun computeAxisY(parent: UIParentNode, child: UIChildNode): Int
}