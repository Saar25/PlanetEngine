package org.saar.core.common.normalmap

import org.saar.core.mesh.Mesh
import org.saar.core.mesh.Model
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture
import org.saar.maths.transform.SimpleTransform
import org.saar.maths.transform.Transform

class NormalMappedModel(
    override val mesh: Mesh,
    val texture: ReadOnlyTexture,
    val normalMap: ReadOnlyTexture,
    val transform: Transform,
) : Model {

    constructor(mesh: Mesh, texture: ReadOnlyTexture, normalMap: ReadOnlyTexture)
            : this(mesh, texture, normalMap, SimpleTransform())

}