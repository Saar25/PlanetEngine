package org.saar.gui.style.value

import org.saar.gui.UINode
import org.saar.gui.UIParentNode

interface AlignmentValue {
    fun computeAxisX(parent: UIParentNode, child: UINode): Int

    fun computeAxisY(parent: UIParentNode, child: UINode): Int
}