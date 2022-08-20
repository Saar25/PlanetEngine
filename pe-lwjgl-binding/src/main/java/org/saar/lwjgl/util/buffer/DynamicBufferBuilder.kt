package org.saar.lwjgl.util.buffer

import org.saar.lwjgl.util.ByteListWriter
import org.saar.lwjgl.util.DataWriter

class DynamicBufferBuilder : BufferBuilder {

    private val storage = mutableListOf<Byte>()

    override val writer: DataWriter = ByteListWriter(this.storage)

    override fun build(): LwjglBuffer = LwjglByteBuffer.allocate(this.storage.size)
        .also { buffer -> this.storage.forEach { byte -> buffer.put(byte) } }

    override fun delete() = this.storage.clear()
}