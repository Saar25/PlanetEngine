package org.saar.lwjgl.opengl.texture

import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.constants.FormatType
import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.util.buffer.LwjglByteBuffer

class ColourTexture private constructor(
    private val texture: ReadOnlyTexture,
    val red: Int,
    val green: Int,
    val blue: Int,
    val alpha: Int
) : ReadOnlyTexture2D {

    override fun bind(unit: Int) = this.texture.bind(unit)

    override fun bind() = this.texture.bind()

    override fun unbind() = this.texture.unbind()

    override fun delete() = this.texture.delete()

    override fun getWidth(): Int = 1

    override fun getHeight(): Int = 1

    companion object {
        @JvmStatic
        fun of(r: Int, g: Int, b: Int, a: Int): ColourTexture {
            LwjglByteBuffer.allocate(4).use { buffer ->
                buffer.put(r.toByte()).put(g.toByte()).put(b.toByte()).put(a.toByte()).flip()
                val texture = Texture2D.of(1, 1, InternalFormat.RGBA2, 1).apply {
                    load(0, FormatType.RGBA, DataType.U_BYTE, buffer.asByteBuffer())
                }
                return ColourTexture(texture, r, g, b, a)
            }
        }
    }
}