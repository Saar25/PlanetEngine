package org.saar.lwjgl.stb

import org.lwjgl.stb.STBTTBakedChar
import org.lwjgl.stb.STBTruetype
import org.lwjgl.system.MemoryStack
import org.saar.lwjgl.util.buffer.LwjglByteBuffer
import org.saar.lwjgl.util.buffer.ReadonlyLwjglBuffer
import org.saar.maths.Box2i

object TrueTypeFontLoader {

    fun bakeFontBitmap(buffer: ReadonlyLwjglBuffer, fontHeight: Float, bitmapWidth: Int,
                       bitmapHeight: Int, start: Char, count: Int): TrueTypeBitmap {
        MemoryStack.stackPush().use { stack ->
            val cdata = STBTTBakedChar.mallocStack(count, stack)
            val bitmap = LwjglByteBuffer.allocate(bitmapWidth * bitmapHeight)

            STBTruetype.stbtt_BakeFontBitmap(buffer.asByteBuffer(), fontHeight,
                bitmap.asByteBuffer(), bitmapWidth, bitmapHeight, start.code, cdata)

            val characters = sequence {
                for (i in 0 until cdata.limit()) {
                    val bakedChar = cdata.get()
                    val localBox = Box2i(
                        bakedChar.x0().toInt(),
                        bakedChar.y0().toInt(),
                        bakedChar.x1().toInt(),
                        bakedChar.y1().toInt())

                    // TODO: find the bitmap box
                    yield(TrueTypeCharacter(start + i,
                        localBox.offset(1, 1), localBox, bakedChar.xadvance()))
                }
            }.toList()

            return TrueTypeBitmap(bitmap, characters, 0f)
        }
    }

    fun bakeFontBitmap(buffer: ReadonlyLwjglBuffer, fontHeight: Float, bitmapWidth: Int,
                       bitmapHeight: Int, charSequence: CharSequence): TrueTypeBitmap {
        TrueTypeFontInfo.create(buffer.asByteBuffer(), fontHeight).use { font ->
            val noDistinctChars = charSequence.toSet().joinToString("")
            return font.createCodepointBitmapSubpixel(bitmapWidth, bitmapHeight, noDistinctChars)
        }
    }
}