package org.saar.gui.style

object Colours {

    @JvmStatic
    fun parse(value: String): Colour {
        val hex = if (value.startsWith("#")) value.substring(1) else value
        return when (hex.length) {
            6 -> parsePackedRGB(value.toInt(16))
            8 -> parsePackedRGBA(value.toInt(16))
            else -> throw IllegalArgumentException("Cannot parse colour $value")
        }
    }

    @JvmStatic
    fun parsePackedRGB(rgb: Int): Colour {
        val r = (rgb shr 0x10 and 255)
        val g = (rgb shr 0x08 and 255)
        val b = (rgb and 0xFF)

        return Colour(r, g, b, 1f)
    }

    @JvmStatic
    fun parsePackedRGBA(rgba: Int): Colour {
        val r = (rgba shr 0x18 and 0xFF)
        val g = (rgba shr 0x10 and 0xFF)
        val b = (rgba shr 0x08 and 0xFF)
        val a = (rgba and 0xFF) / 255f

        return Colour(r, g, b, a)
    }

    @JvmField
    @Suppress("unused")
    val TRANSPARENT: Colour = Colour(0, 0, 0, 0f)

    @JvmField
    @Suppress("unused")
    val WHITE: Colour = Colour(255, 255, 255, 1f)

    @JvmField
    @Suppress("unused")
    val LIGHT_GREY: Colour = Colour(179, 179, 179, 1f)

    @JvmField
    @Suppress("unused")
    val GREY: Colour = Colour(127, 127, 127, 1f)

    @JvmField
    @Suppress("unused")
    val DARK_GREY: Colour = Colour(77, 77, 77, 1f)

    @JvmField
    @Suppress("unused")
    val BLACK: Colour = Colour(0, 0, 0, 1f)

    @JvmField
    @Suppress("unused")
    val RED: Colour = Colour(255, 0, 0, 1f)

    @JvmField
    @Suppress("unused")
    val GREEN: Colour = Colour(0, 255, 0, 1f)

    @JvmField
    @Suppress("unused")
    val BLUE: Colour = Colour(0, 0, 255, 1f)

    @JvmField
    @Suppress("unused")
    val PURPLE: Colour = Colour(255, 0, 255, 1f)

    @JvmField
    @Suppress("unused")
    val CYAN: Colour = Colour(0, 255, 255, 1f)

    @JvmField
    @Suppress("unused")
    val YELLOW: Colour = Colour(255, 255, 0, 1f)

    @JvmField
    @Suppress("unused")
    val ORANGE: Colour = Colour(255, 127, 0, 1f)

    @JvmField
    @Suppress("unused")
    val BROWN: Colour = Colour(139, 69, 19, 1f)

}