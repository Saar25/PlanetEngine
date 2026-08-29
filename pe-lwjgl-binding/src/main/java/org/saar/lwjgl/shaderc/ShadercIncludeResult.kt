package org.saar.lwjgl.shaderc

import org.lwjgl.system.MemoryUtil
import java.nio.ByteBuffer
import org.lwjgl.util.shaderc.ShadercIncludeResult as NativeIncludeResult

class ShadercIncludeResult private constructor(
    private val native: NativeIncludeResult,
) : AutoCloseable {

    val sourceName: String
        get() = MemoryUtil.memUTF8Safe(this.native.source_name()) ?: ""

    val content: String
        get() = MemoryUtil.memUTF8Safe(this.native.content()) ?: ""

    val contentLength: Long
        get() = this.native.content_length()

    fun address(): Long = this.native.address()

    override fun close() {
        MemoryUtil.memFree(this.native.source_name())
        MemoryUtil.memFree(this.native.content())
        this.native.free()
    }

    companion object {
        fun of(address: Long): ShadercIncludeResult {
            val native = NativeIncludeResult.create(address)

            return ShadercIncludeResult(native)
        }

        fun of(sourceName: String, content: String): ShadercIncludeResult {
            val sourceNameBuffer = MemoryUtil.memUTF8(sourceName, true)
            val contentBuffer = MemoryUtil.memUTF8(content, true)
            return of(sourceNameBuffer, contentBuffer)
        }

        fun of(sourceName: ByteBuffer, content: ByteBuffer): ShadercIncludeResult {
            val native = NativeIncludeResult.calloc()
                .source_name(sourceName)
                .content(content)

            return ShadercIncludeResult(native)
        }
    }
}
