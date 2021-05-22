package org.saar.core.common.r3d

import org.saar.core.mesh.Model
import org.saar.maths.transform.SimpleTransform

class Model3D(override val mesh: Mesh3D, val transform: SimpleTransform) : Model {
    constructor(mesh: Mesh3D) : this(mesh, SimpleTransform())

    var specular: Float = 1f
}