package org.saar.gui.style.property

class ImmutableColour(
        override val red: Float,
        override val green: Float,
        override val blue: Float,
        override val alpha: Float) : IColour {

    override fun toString() = asString()
}