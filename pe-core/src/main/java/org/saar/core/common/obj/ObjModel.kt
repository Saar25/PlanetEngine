package org.saar.core.common.obj

import org.saar.core.mesh.Model
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture
import org.saar.maths.transform.SimpleTransform
import org.saar.maths.transform.Transform

class ObjModel(override val mesh: ObjMesh, val texture: ReadOnlyTexture, val transform: Transform) : Model {
    constructor(mesh: ObjMesh, texture: ReadOnlyTexture) : this(mesh, texture, SimpleTransform())
}