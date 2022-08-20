package org.saar.lwjgl.util.buffer

import org.saar.lwjgl.util.DataWriter

interface BufferBuilder {

    val writer: DataWriter

    fun build(): LwjglBuffer

    fun delete()

}