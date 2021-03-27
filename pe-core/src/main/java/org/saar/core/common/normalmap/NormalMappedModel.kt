package org.saar.core.common.normalmap

import org.saar.core.mesh.Model
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture
import org.saar.maths.transform.SimpleTransform
import org.saar.maths.transform.Transform

class NormalMappedModel(override val mesh: NormalMappedMesh,
                        val texture: ReadOnlyTexture,
                        val normalMap: ReadOnlyTexture,
                        val transform: Transform) : Model {

    constructor(mesh: NormalMappedMesh, texture: ReadOnlyTexture, normalMap: ReadOnlyTexture)
            : this(mesh, texture, normalMap, SimpleTransform())

}