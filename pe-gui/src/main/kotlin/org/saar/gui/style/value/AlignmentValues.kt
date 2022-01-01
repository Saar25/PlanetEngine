package org.saar.gui.style.value

import org.saar.gui.UIChildElement
import org.saar.gui.UIContainer

object AlignmentValues {

    @JvmStatic
    fun horizontal() = object : AlignmentValue {
        override fun computeAxisX(parent: UIContainer, child: UIChildElement, index: Int): Int {
            return 0
        }

        override fun computeAxisY(parent: UIContainer, child: UIChildElement, index: Int): Int {
            return 0
        }
    }
}