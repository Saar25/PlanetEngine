package org.saar.lwjgl.stb

import org.saar.lwjgl.util.buffer.LwjglByteBuffer

data class TrueTypeCharacterBitmap(val bitmap: LwjglByteBuffer,
                                   val width: Int,
                                   val height: Int,
                                   val xOffset: Int,
                                   val yOffset: Int) : AutoCloseable {

    override fun close() = this.bitmap.close()
}
