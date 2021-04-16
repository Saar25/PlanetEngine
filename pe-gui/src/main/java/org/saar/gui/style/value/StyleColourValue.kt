package org.saar.gui.style.value

import org.saar.gui.style.Colour

fun interface StyleColourValue {
    fun compute(parent: Colour): Colour
}