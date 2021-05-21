package org.saar.gui.font

import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.constants.FormatType
import org.saar.lwjgl.opengl.textures.Texture2D
import org.saar.lwjgl.opengl.textures.parameters.WrapParameter
import org.saar.lwjgl.opengl.textures.settings.TextureMipMapSetting
import org.saar.lwjgl.opengl.textures.settings.TextureSWrapSetting
import org.saar.lwjgl.opengl.textures.settings.TextureTWrapSetting
import org.saar.lwjgl.stb.TrueTypeFontLoader
import org.saar.lwjgl.util.buffer.LwjglByteBuffer
import org.saar.lwjgl.util.buffer.ReadonlyLwjglBuffer
import java.io.File

object FontLoader {

    private fun File.readToLwjglBuffer(): ReadonlyLwjglBuffer {
        return readBytes().let { LwjglByteBuffer.allocate(it.size).put(it).flip() }
    }

    @JvmStatic
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

    @JvmStatic
    fun loadFont(fontFile: String, fontHeight: Float, bitmapWidth: Int,
                 bitmapHeight: Int, charSequence: CharSequence): Font {
        File(fontFile).readToLwjglBuffer().use { buffer ->
            val (bitmap, characters) = TrueTypeFontLoader.bakeFontBitmap(
                buffer, fontHeight, bitmapWidth, bitmapHeight, charSequence)

            return object : Font {
                override val bitmap = bitmapToTexture(bitmap, bitmapWidth, bitmapHeight)

                override val characters = characters.map {
                    FontCharacter(it.char, it.x0, it.y0, it.x1, it.y1, it.xAdvance)
                }
            }
        }
    }

}