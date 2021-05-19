package org.saar.lwjgl.stb

import org.lwjgl.stb.STBTTBakedChar
import org.lwjgl.stb.STBTruetype
import org.lwjgl.system.MemoryStack
import org.saar.lwjgl.util.buffer.LwjglByteBuffer
import java.nio.ByteBuffer

object TrueTypeFontLoader {

    fun bakeFontBitmap(buffer: ByteBuffer, fontHeight: Float, bitmapWidth: Int,
                       bitmapHeight: Int, start: Char, count: Int): TrueTypeBitmap {
        MemoryStack.stackPush().use { stack ->
            val cdata = STBTTBakedChar.mallocStack(count, stack)
            val bitmap = LwjglByteBuffer.allocate(bitmapWidth * bitmapHeight)

            STBTruetype.stbtt_BakeFontBitmap(buffer, fontHeight,
                bitmap.asByteBuffer(), bitmapWidth, bitmapHeight, start.toInt(), cdata)

            val characters = sequence {
                for (i in 0 until cdata.limit()) {
                    yield(TrueTypeCharacter.of(start + i, cdata.get()))
                }
            }.toList()

            return TrueTypeBitmap(bitmap, characters)
        }
    }

    fun bakeFontBitmap(buffer: LwjglByteBuffer, fontHeight: Float, bitmapWidth: Int,
                       bitmapHeight: Int, charSequence: CharSequence): TrueTypeBitmap {
        TrueTypeFontInfo.create(buffer.asByteBuffer(), fontHeight).use { font ->
            return font.createCodepointBitmapSubpixel(bitmapWidth, bitmapHeight, charSequence)
        }
    }
}