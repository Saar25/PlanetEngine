package org.saar.gui.style

object Colors {

    @JvmStatic
    fun parse(value: String): Color {
        val hex = value.removePrefix("#")
        return when (hex.length) {
            6 -> parsePackedRGB(hex.toInt(16))
            8 -> parsePackedRGBA(hex.toInt(16))
            else -> throw IllegalArgumentException("Cannot parse color $value")
        }
    }

    @JvmStatic
    fun parsePackedRGB(rgb: Int): Color {
        val r = (rgb shr 0x10 and 255)
        val g = (rgb shr 0x08 and 255)
        val b = (rgb and 0xFF)

        return Color(r, g, b, 1f)
    }

    @JvmStatic
    fun parsePackedRGBA(rgba: Int): Color {
        val r = (rgba shr 0x18 and 0xFF)
        val g = (rgba shr 0x10 and 0xFF)
        val b = (rgba shr 0x08 and 0xFF)
        val a = (rgba and 0xFF) / 255f

        return Color(r, g, b, a)
    }

    @JvmField
    @Suppress("unused")
    val TRANSPARENT: Color = Color(0, 0, 0, 0f)

    @JvmField
    @Suppress("unused")
    val WHITE: Color = Color(255, 255, 255, 1f)

    @JvmField
    @Suppress("unused")
    val LIGHT_GRAY: Color = Color(179, 179, 179, 1f)

    @JvmField
    @Suppress("unused")
    val GRAY: Color = Color(127, 127, 127, 1f)

    @JvmField
    @Suppress("unused")
    val DARK_GRAY: Color = Color(77, 77, 77, 1f)

    @JvmField
    @Suppress("unused")
    val BLACK: Color = Color(0, 0, 0, 1f)

    @JvmField
    @Suppress("unused")
    val RED: Color = Color(255, 0, 0, 1f)

    @JvmField
    @Suppress("unused")
    val GREEN: Color = Color(0, 255, 0, 1f)

    @JvmField
    @Suppress("unused")
    val BLUE: Color = Color(0, 0, 255, 1f)

    @JvmField
    @Suppress("unused")
    val PURPLE: Color = Color(255, 0, 255, 1f)

    @JvmField
    @Suppress("unused")
    val CYAN: Color = Color(0, 255, 255, 1f)

    @JvmField
    @Suppress("unused")
    val YELLOW: Color = Color(255, 255, 0, 1f)

    @JvmField
    @Suppress("unused")
    val ORANGE: Color = Color(255, 127, 0, 1f)

    @JvmField
    @Suppress("unused")
    val BROWN: Color = Color(139, 69, 19, 1f)

}