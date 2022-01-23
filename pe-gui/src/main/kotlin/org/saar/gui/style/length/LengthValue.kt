package org.saar.gui.style.length

import org.saar.gui.UIChildNode

interface LengthValue {
    fun computeAxisX(container: UIChildNode): Int

    fun computeAxisY(container: UIChildNode): Int

    fun computeMinAxisX(container: UIChildNode): Int

    fun computeMinAxisY(container: UIChildNode): Int
}