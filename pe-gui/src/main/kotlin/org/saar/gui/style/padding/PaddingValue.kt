package org.saar.gui.style.padding

import org.saar.gui.UIChildNode

interface PaddingValue {
    fun computeTop(container: UIChildNode): Int

    fun computeRight(container: UIChildNode): Int

    fun computeBottom(container: UIChildNode): Int

    fun computeLeft(container: UIChildNode): Int
}