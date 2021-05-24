package org.saar.gui.font

import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.constants.FormatType
import org.saar.lwjgl.opengl.textures.Texture2D
import org.saar.lwjgl.opengl.textures.parameters.WrapParameter
import org.saar.lwjgl.opengl.textures.settings.TextureMipMapSetting
import org.saar.lwjgl.opengl.textures.settings.TextureSWrapSetting
import org.saar.lwjgl.opengl.textures.settings.TextureTWrapSetting
import org.saar.lwjgl.stb.TrueTypeBitmap
import org.saar.lwjgl.stb.TrueTypeFontLoader
import org.saar.lwjgl.util.buffer.LwjglByteBuffer
import org.saar.lwjgl.util.buffer.ReadonlyLwjglBuffer
import java.io.File

object FontLoader {

    val DEFAULT_FONT: Font by lazy { loadFont("C:/Windows/Fonts/segoeui.ttf", 48f, 512, 512) }

    private val DEFAULT_CHAR_SEQUENCE = (0x20.toChar()..0x7e.toChar()).joinToString("")

    private fun File.readToLwjglBuffer(): ReadonlyLwjglBuffer {
        return readBytes().let { LwjglByteBuffer.allocate(it.size).put(it).flip() }
    }

    private fun bitmapToTexture(bitmap: LwjglByteBuffer, width: Int, height: Int): Texture2D {
        return Texture2D.of(width, height).also { texture ->
            texture.load(bitmap.asByteBuffer(), FormatType.RED, DataType.U_BYTE)
            texture.setSettings(
                TextureSWrapSetting(WrapParameter.CLAMP_TO_BORDER),
                TextureTWrapSetting(WrapParameter.CLAMP_TO_BORDER),
                TextureMipMapSetting()
            )
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
    fun loadFont(fontFile: String, fontHeight: Float, bitmapWidth: Int,
                 bitmapHeight: Int, charSequence: CharSequence): Font {
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
    fun loadFont(fontFile: String, fontHeight: Float, bitmapWidth: Int,
                 bitmapHeight: Int, start: Char, count: Int): Font {
        val charSequence = (start..start + count).joinToString("")
        return loadFont(fontFile, fontHeight, bitmapWidth, bitmapHeight, charSequence)
    }

}