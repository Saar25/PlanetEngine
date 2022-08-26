package org.saar.core.common.particles

import org.saar.core.mesh.Mesh
import org.saar.core.mesh.Model
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D
import org.saar.maths.transform.SimpleTransform

class ParticlesModel @JvmOverloads constructor(
    override val mesh: Mesh,
    val texture: ReadOnlyTexture2D,
    val textureAtlasSize: Int,
    val maxAge: Int,
    val transform: SimpleTransform = SimpleTransform(),
) : Model