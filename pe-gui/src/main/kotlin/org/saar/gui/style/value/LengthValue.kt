package org.saar.gui.style.value

import org.saar.gui.style.Style

interface LengthValue {
    fun computeAxisX(parent: Style, style: Style): Int

    fun computeAxisY(parent: Style, style: Style): Int

    fun interface Simple : LengthValue {
        fun compute(parent: Style, style: Style): Int

        override fun computeAxisX(parent: Style, style: Style) = compute(parent, style)
        override fun computeAxisY(parent: Style, style: Style) = compute(parent, style)
    }
}