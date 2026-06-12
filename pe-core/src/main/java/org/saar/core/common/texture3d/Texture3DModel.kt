package org.saar.core.common.texture3d

import org.saar.core.mesh.Mesh
import org.saar.core.mesh.Model
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture
import org.saar.maths.transform.SimpleTransform

class Texture3DModel(override val mesh: Mesh, val transform: SimpleTransform, val texture: ReadOnlyTexture) : Model {
    constructor(mesh: Mesh, texture: ReadOnlyTexture) : this(mesh, SimpleTransform(), texture)

    var specular: Float = 0f
}