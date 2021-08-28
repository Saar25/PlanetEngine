package org.saar.lwjgl.stb

import org.lwjgl.stb.STBTTFontinfo
import org.lwjgl.stb.STBTruetype
import org.lwjgl.system.MemoryStack
import org.saar.lwjgl.stb.exceptions.STBBufferOverflowException
import org.saar.lwjgl.stb.exceptions.STBInitializationException
import org.saar.lwjgl.util.buffer.LwjglByteBuffer
import org.saar.utils.Box2i
import java.nio.ByteBuffer

class TrueTypeFontInfo private constructor(private val info: STBTTFontinfo, private val scale: Float) : AutoCloseable {

    private fun Float.fraction() = this - this.toInt()

    companion object {
        fun create(buffer: ByteBuffer, fontHeight: Float): TrueTypeFontInfo {
            val info = STBTTFontinfo.malloc()

            if (!STBTruetype.stbtt_InitFont(info, buffer, 0)) {
                throw STBInitializationException("Failed to initialize font information")
            }

            val scale = STBTruetype.stbtt_ScaleForPixelHeight(info, fontHeight)

            return TrueTypeFontInfo(info, scale)
        }
    }

    fun getFontVMetrics(): TrueTypeFontVMetrics {
        MemoryStack.stackPush().use { stack ->
            val ascentBuffer = stack.mallocInt(1)
            val descentBuffer = stack.mallocInt(1)
            val lineGapBuffer = stack.mallocInt(1)

            STBTruetype.stbtt_GetFontVMetrics(this.info,
                ascentBuffer, descentBuffer, lineGapBuffer)

            return TrueTypeFontVMetrics(ascentBuffer.get(),
                descentBuffer.get(), lineGapBuffer.get())
        }
    }

    fun getCodepointHMetrics(char: Char): TrueTypeCodepointHMetrics {
        MemoryStack.stackPush().use { stack ->
            val advanceWidthBuffer = stack.mallocInt(1)
            val leftSideBearingBuffer = stack.mallocInt(1)

            STBTruetype.stbtt_GetCodepointHMetrics(this.info,
                char.code, advanceWidthBuffer, leftSideBearingBuffer)

            return TrueTypeCodepointHMetrics(advanceWidthBuffer.get(), leftSideBearingBuffer.get())
        }
    }

    fun getCodepointBitmapBox(char: Char): Box2i {
        MemoryStack.stackPush().use { stack ->
            val x0Buffer = stack.mallocInt(1)
            val y0Buffer = stack.mallocInt(1)
            val x1Buffer = stack.mallocInt(1)
            val y1Buffer = stack.mallocInt(1)

            STBTruetype.stbtt_GetCodepointBitmapBox(this.info, char.code,
                this.scale, this.scale, x0Buffer, y0Buffer, x1Buffer, y1Buffer)

            return Box2i(x0Buffer.get(), y0Buffer.get(), x1Buffer.get(), y1Buffer.get())
        }
    }

    fun getCodepointBitmapBoxSubpixel(char: Char, xShift: Float, yShift: Float): Box2i {
        MemoryStack.stackPush().use { stack ->
            val x0Buffer = stack.mallocInt(1)
            val y0Buffer = stack.mallocInt(1)
            val x1Buffer = stack.mallocInt(1)
            val y1Buffer = stack.mallocInt(1)

            STBTruetype.stbtt_GetCodepointBitmapBoxSubpixel(this.info, char.code,
                this.scale, this.scale, xShift, yShift, x0Buffer, y0Buffer, x1Buffer, y1Buffer)

            return Box2i(x0Buffer.get(), y0Buffer.get(), x1Buffer.get(), y1Buffer.get())
        }
    }

    fun getCodePointBitmap(char: Char): TrueTypeCharacterBitmap {
        MemoryStack.stackPush().use { stack ->
            val wBuffer = stack.mallocInt(1)
            val hBuffer = stack.mallocInt(1)
            val xBuffer = stack.mallocInt(1)
            val yBuffer = stack.mallocInt(1)

            val bitmap = STBTruetype.stbtt_GetCodepointBitmap(this.info,
                this.scale, this.scale, char.code, wBuffer, hBuffer, xBuffer, yBuffer)!!

            return TrueTypeCharacterBitmap(LwjglByteBuffer.wrap(bitmap),
                wBuffer.get(), hBuffer.get(), xBuffer.get(), yBuffer.get())
        }
    }

    fun makeCodepointBitmapSubpixel(bitmap: ByteBuffer, bitmapWidth: Int,
                                    x: Float, y: Float, box: Box2i, char: Char) {
        val position = (y.toInt() + box.y0) * bitmapWidth + x.toInt() + box.x0

        if (position >= bitmap.limit()) {
            throw STBBufferOverflowException("Bitmap is too small, position: $position, limit: ${bitmap.limit()}")
        }
        bitmap.position(position)

        STBTruetype.stbtt_MakeCodepointBitmapSubpixel(this.info, bitmap, box.width,
            box.height, bitmapWidth, this.scale, this.scale, x.fraction(), 0f, char.code)
    }

    fun createCodepointBitmapSubpixel(width: Int, height: Int, charSequence: CharSequence): TrueTypeBitmap {
        val bitmap = LwjglByteBuffer.callocate(width * height)

        var x = 0f

        val (ascent, descent, lineGap) = getFontVMetrics()
        val fontHeight = (ascent - descent + lineGap) * this.scale

        var y = fontHeight

        val characters = mutableListOf<TrueTypeCharacter>()

        for (char in charSequence) {
            val (advance) = getCodepointHMetrics(char)

            val toAdvance = advance * this.scale

            if (x + toAdvance > width) {
                y += fontHeight
                x = 0f
            }

            val box = getCodepointBitmapBoxSubpixel(char, x.fraction(), 0f)
            makeCodepointBitmapSubpixel(bitmap.asByteBuffer(), width, x, y, box, char)

            characters.add(TrueTypeCharacter(char, box.offset(x.toInt(), y.toInt()), box, toAdvance))

            x += toAdvance
        }

        return TrueTypeBitmap(bitmap.clear(), characters, lineGap * this.scale)
    }

    override fun close() = this.info.free()

}