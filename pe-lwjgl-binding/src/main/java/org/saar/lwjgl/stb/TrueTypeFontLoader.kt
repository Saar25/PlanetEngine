package org.saar.lwjgl.stb

import org.lwjgl.stb.STBTTBakedChar
import org.lwjgl.stb.STBTruetype
import org.lwjgl.system.MemoryStack
import org.saar.lwjgl.util.buffer.LwjglByteBuffer
import org.saar.lwjgl.util.buffer.ReadonlyLwjglBuffer

object TrueTypeFontLoader {

    fun bakeFontBitmap(buffer: ReadonlyLwjglBuffer, fontHeight: Float, bitmapWidth: Int,
                       bitmapHeight: Int, start: Char, count: Int): TrueTypeBitmap {
        MemoryStack.stackPush().use { stack ->
            val cdata = STBTTBakedChar.mallocStack(count, stack)
            val bitmap = LwjglByteBuffer.allocate(bitmapWidth * bitmapHeight)

            STBTruetype.stbtt_BakeFontBitmap(buffer.asByteBuffer(), fontHeight,
                bitmap.asByteBuffer(), bitmapWidth, bitmapHeight, start.toInt(), cdata)

            val characters = sequence {
                for (i in 0 until cdata.limit()) {
                    val bakedChar = cdata.get()
                    yield(TrueTypeCharacter(start + i,
                        bakedChar.x0(), bakedChar.y0(),
                        bakedChar.x1(), bakedChar.y1(),
                        bakedChar.xadvance()))
                }
            }.toList()

            return TrueTypeBitmap(bitmap, characters)
        }
    }

    fun bakeFontBitmap(buffer: ReadonlyLwjglBuffer, fontHeight: Float, bitmapWidth: Int,
                       bitmapHeight: Int, charSequence: CharSequence): TrueTypeBitmap {
        TrueTypeFontInfo.create(buffer.asByteBuffer(), fontHeight).use { font ->
            return font.createCodepointBitmapSubpixel(bitmapWidth, bitmapHeight, charSequence)
        }
    }
}