package org.saar.lwjgl.opengl.texture

class MultiTexture(
    private val blendMap: ReadOnlyTexture, // Blend Map Texture
    private val dTexture: ReadOnlyTexture, // Default   Texture
    private val rTexture: ReadOnlyTexture, // Red       Texture
    private val gTexture: ReadOnlyTexture, // Green     Texture
    private val bTexture: ReadOnlyTexture, // Blue      Texture
) : ReadOnlyTexture {

    override fun bind(unit: Int) = forEach { texture, index -> texture.bind(unit + index) }

    override fun bind() = bind(0)

    override fun unbind() = forEach { obj, _ -> obj.unbind() }

    override fun delete() = forEach { obj, _ -> obj.delete() }

    private inline fun forEach(consumer: (ReadOnlyTexture, Int) -> Unit) {
        consumer(this.blendMap, 0)
        consumer(this.dTexture, 1)
        consumer(this.rTexture, 2)
        consumer(this.gTexture, 3)
        consumer(this.bTexture, 4)
    }
}