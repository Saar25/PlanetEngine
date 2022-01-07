package org.saar.gui.style.value

import org.saar.gui.style.Style

fun interface FontSizeValue {

    fun compute(parent: Style, style: Style): Int
}