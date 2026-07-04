package org.saar.lwjgl.opengl.texture

import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.constants.FormatType
import org.saar.lwjgl.opengl.constants.InternalFormat
import org.saar.lwjgl.util.buffer.LwjglByteBuffer

class ColorTexture private constructor(
    private val texture: TextureObject,
    val red: Int,
    val green: Int,
    val blue: Int,
    val alpha: Int
) : ReadOnlyTexture2D {

    override fun bind(unit: Int) = this.texture.bind(TextureTarget.TEXTURE_2D, unit)

    override fun bind() = this.texture.bind(TextureTarget.TEXTURE_2D)

    override fun delete() = this.texture.delete()

    override fun getWidth(): Int = 1

    override fun getHeight(): Int = 1

    companion object {
        @JvmStatic
        fun of(r: Int, g: Int, b: Int, a: Int): ColorTexture {
            LwjglByteBuffer.allocate(4).use { buffer ->
                buffer.put(r.toByte()).put(g.toByte()).put(b.toByte()).put(a.toByte()).flip()
                val texture = TextureObject.create().apply {
                    allocate(TextureTarget.TEXTURE_2D, 1, InternalFormat.RGBA8, 1, 1)
                    load(
                        TextureTarget.TEXTURE_2D, 0,
                        0, 0, 1, 1,
                        FormatType.RGBA, DataType.U_BYTE, buffer.asByteBuffer()
                    )
                }
                return ColorTexture(texture, r, g, b, a)
            }
        }
    }
}