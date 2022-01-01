package org.saar.gui.style.value

import org.saar.gui.UIChildElement
import org.saar.gui.UIContainer

interface AlignmentValue {
    fun computeAxisX(parent: UIContainer, child: UIChildElement, index: Int): Int

    fun computeAxisY(parent: UIContainer, child: UIChildElement, index: Int): Int
}