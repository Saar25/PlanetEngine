package org.saar.gui.style.colour

import org.saar.gui.style.StyleProperty

object WindowColour : StyleProperty, ReadonlyColour {

    override val red: Float = 0f
    override val green: Float = 0f
    override val blue: Float = 0f
    override val alpha: Float = 1f

    override fun toString() = asString()
}