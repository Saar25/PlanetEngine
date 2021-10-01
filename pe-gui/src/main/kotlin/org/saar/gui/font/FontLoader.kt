package org.saar.gui.font

import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.constants.FormatType
import org.saar.lwjgl.opengl.texture.Texture2D
import org.saar.lwjgl.opengl.texture.parameter.*
import org.saar.lwjgl.opengl.texture.values.MagFilterValue
import org.saar.lwjgl.opengl.texture.values.MinFilterValue
import org.saar.lwjgl.opengl.texture.values.WrapValue
import org.saar.lwjgl.stb.TrueTypeBitmap
import org.saar.lwjgl.stb.TrueTypeFontLoader
import org.saar.lwjgl.util.buffer.LwjglByteBuffer
import org.saar.lwjgl.util.buffer.ReadonlyLwjglBuffer
import java.io.File

object FontLoader {

    @JvmField
    val DEFAULT_FONT_FAMILY =
        if (System.getProperty("os.name").contains("win")) "C:/Windows/Fonts/segoeui.ttf"
        else if (System.getProperty("os.name").contains("nux")) "/usr/share/fonts/truetype/ubuntu/Ubuntu-L.ttf"
        else if (System.getProperty("os.name").contains("nix")) "/usr/share/fonts/truetype/ubuntu/Ubuntu-L.ttf"
        else ""

    @JvmStatic
    val DEFAULT_FONT: Font by lazy { loadFont(DEFAULT_FONT_FAMILY, 48f, 512, 512) }

    private val DEFAULT_CHAR_SEQUENCE = (0x20.toChar()..0x7e.toChar()).joinToString("")

    private fun File.readToLwjglBuffer(): ReadonlyLwjglBuffer {
        return readBytes().let { LwjglByteBuffer.allocate(it.size).put(it).flip() }
    }

    private fun bitmapToTexture(bitmap: LwjglByteBuffer, width: Int, height: Int): Texture2D {
        return Texture2D.of(width, height).also { texture ->
            texture.load(0, FormatType.RED, DataType.U_BYTE, bitmap.asByteBuffer())
            texture.applyParameters(arrayOf<TextureParameter>(
                TextureSWrapParameter(WrapValue.CLAMP_TO_BORDER),
                TextureTWrapParameter(WrapValue.CLAMP_TO_BORDER),
                TextureMagFilterParameter(MagFilterValue.LINEAR),
                TextureMinFilterParameter(MinFilterValue.NEAREST),
            ))
        }
    }

    private fun toFont(trueTypeBitmap: TrueTypeBitmap, bitmapWidth: Int, bitmapHeight: Int, fontHeight: Float): Font {
        return object : Font {
            override val size = fontHeight

            override val bitmap = bitmapToTexture(trueTypeBitmap.bitmap, bitmapWidth, bitmapHeight)

            override val characters = trueTypeBitmap.characters.map {
                FontCharacter(it.char, it.bitmapBox, it.localBox, it.xAdvance)
            }

            override val lineGap = trueTypeBitmap.lineGap
        }
    }

    @JvmStatic
    fun loadFont(
        fontFile: String, fontHeight: Float, bitmapWidth: Int,
        bitmapHeight: Int, charSequence: CharSequence
    ): Font {
        val trueTypeBitmap = File(fontFile).readToLwjglBuffer().use { buffer ->
            TrueTypeFontLoader.bakeFontBitmap(buffer, fontHeight, bitmapWidth, bitmapHeight, charSequence)
        }
        return toFont(trueTypeBitmap, bitmapWidth, bitmapHeight, fontHeight)
    }

    @JvmStatic
    fun loadFont(fontFile: String, fontHeight: Float, bitmapWidth: Int, bitmapHeight: Int): Font {
        return loadFont(fontFile, fontHeight, bitmapWidth, bitmapHeight, DEFAULT_CHAR_SEQUENCE)
    }

    @JvmStatic
    fun loadFont(
        fontFile: String, fontHeight: Float, bitmapWidth: Int,
        bitmapHeight: Int, start: Char, count: Int
    ): Font {
        val charSequence = (start..start + count).joinToString("")
        return loadFont(fontFile, fontHeight, bitmapWidth, bitmapHeight, charSequence)
    }

}