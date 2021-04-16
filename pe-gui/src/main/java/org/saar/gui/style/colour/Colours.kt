package org.saar.gui.style.colour

object Colours {

    @JvmField
    @Suppress("unused")
    val TRANSPARENT: ReadonlyColour = ImmutableColour(0f, 0f, 0f, 0f)

    @JvmField
    @Suppress("unused")
    val WHITE: ReadonlyColour = ImmutableColour(1f, 1f, 1f, 1f)

    @JvmField
    @Suppress("unused")
    val LIGHT_GREY: ReadonlyColour = ImmutableColour(.7f, .7f, .7f, 1f)

    @JvmField
    @Suppress("unused")
    val GREY: ReadonlyColour = ImmutableColour(.5f, .5f, .5f, 1f)

    @JvmField
    @Suppress("unused")
    val DARK_GREY: ReadonlyColour = ImmutableColour(.3f, .3f, .3f, 1f)

    @JvmField
    @Suppress("unused")
    val BLACK: ReadonlyColour = ImmutableColour(0f, 0f, 0f, 1f)

    @JvmField
    @Suppress("unused")
    val RED: ReadonlyColour = ImmutableColour(1f, 0f, 0f, 1f)

    @JvmField
    @Suppress("unused")
    val GREEN: ReadonlyColour = ImmutableColour(0f, 1f, 0f, 1f)

    @JvmField
    @Suppress("unused")
    val BLUE: ReadonlyColour = ImmutableColour(0f, 0f, 1f, 1f)

    @JvmField
    @Suppress("unused")
    val PURPLE: ReadonlyColour = ImmutableColour(1f, 0f, 1f, 1f)

    @JvmField
    @Suppress("unused")
    val CYAN: ReadonlyColour = ImmutableColour(0f, 1f, 1f, 1f)

    @JvmField
    @Suppress("unused")
    val YELLOW: ReadonlyColour = ImmutableColour(1f, 1f, 0f, 1f)

    @JvmField
    @Suppress("unused")
    val ORANGE: ReadonlyColour = ImmutableColour(1f, .5f, 0f, 1f)

    @JvmField
    @Suppress("unused")
    val BROWN: ReadonlyColour = ImmutableColour(0.39f, 0.26f, 0.13f, 1f)

}

private class ImmutableColour(override val red: Float,
                              override val green: Float,
                              override val blue: Float,
                              override val alpha: Float) : ReadonlyColour {

    override fun toString() = asString()
}