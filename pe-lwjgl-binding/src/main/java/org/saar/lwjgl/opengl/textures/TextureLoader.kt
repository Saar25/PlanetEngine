package org.saar.lwjgl.opengl.textures

import org.lwjgl.stb.STBImage
import org.lwjgl.system.MemoryStack
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.constants.FormatType
import org.saar.lwjgl.util.buffer.LwjglByteBuffer
import java.io.FileNotFoundException
import java.nio.ByteBuffer

object TextureLoader {

    @JvmStatic
    fun load(file: String): TextureInfo {
        val byteBuffer = loadFile(file)

        return loadImage(byteBuffer)
    }

    private fun loadFile(file: String): ByteBuffer {
        val resource = TextureLoader::class.java.getResourceAsStream(file)

        val bytes = (resource ?: throw FileNotFoundException()).use { it.readAllBytes() }

        return LwjglByteBuffer.callocate(bytes.size).put(bytes).flip().asByteBuffer()
    }

    private fun loadImage(byteBuffer: ByteBuffer): TextureInfo {
        MemoryStack.stackPush().use { stack ->
            val width = stack.callocInt(1)
            val height = stack.callocInt(1)
            val channels = stack.callocInt(1)

            val buffer = STBImage.stbi_load_from_memory(byteBuffer,
                width, height, channels, STBImage.STBI_rgb_alpha)!!
            buffer.flip()

            return TextureInfo(width.get(), height.get(), FormatType.RGBA, DataType.BYTE, buffer)
        }
    }
}