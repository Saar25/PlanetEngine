package org.saar.lwjgl.util.buffer

import org.saar.lwjgl.util.DataWriter

class FixedBufferBuilder(capacity: Int) : BufferBuilder {

    private val storage = LwjglByteBuffer.allocate(capacity)

    override val writer: DataWriter = this.storage.writer

    override fun build(): LwjglBuffer = this.storage
}