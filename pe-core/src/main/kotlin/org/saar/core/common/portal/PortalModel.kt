package org.saar.core.common.portal

import org.saar.core.mesh.Mesh
import org.saar.core.mesh.Model
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture
import org.saar.lwjgl.opengl.texture.Texture2D
import org.saar.maths.transform.SimpleTransform

class PortalModel(override val mesh: Mesh, val transform: SimpleTransform) : Model {

    var viewTexture: ReadOnlyTexture = Texture2D.NULL

    constructor(mesh: Mesh) : this(mesh, SimpleTransform())

}