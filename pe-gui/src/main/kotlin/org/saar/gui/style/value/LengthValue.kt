package org.saar.gui.style.value

import org.saar.gui.UIChildNode
import org.saar.gui.UIParentNode

interface LengthValue {
    fun computeAxisX(parent: UIParentNode, container: UIChildNode): Int

    fun computeAxisY(parent: UIParentNode, container: UIChildNode): Int

    fun computeMinAxisX(parent: UIParentNode, container: UIChildNode): Int

    fun computeMinAxisY(parent: UIParentNode, container: UIChildNode): Int
}