package org.saar.lwjgl.stb

import org.saar.lwjgl.util.buffer.LwjglByteBuffer

data class TrueTypeBitmap(val bitmap: LwjglByteBuffer,
                          val characters: List<TrueTypeCharacter>,
                          val lineGap: Float) : AutoCloseable {

    override fun close() = this.bitmap.close()

}
