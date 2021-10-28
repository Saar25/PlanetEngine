package org.saar.core.common.particles

import org.saar.core.mesh.Model
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D
import org.saar.maths.transform.SimpleTransform

class ParticlesModel(
    override val mesh: ParticlesMesh,
    val texture: ReadOnlyTexture2D,
    val textureAtlasSize: Int,
    val transform: SimpleTransform = SimpleTransform()
) : Model {
    constructor(mesh: ParticlesMesh, texture: ReadOnlyTexture2D, textureAtlasSize: Int) :
            this(mesh, texture, textureAtlasSize, SimpleTransform())
}