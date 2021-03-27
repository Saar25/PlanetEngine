package org.saar.core.common.smooth

import org.saar.core.mesh.Model
import org.saar.maths.transform.SimpleTransform

class SmoothModel(override val mesh: SmoothMesh, val transform: SimpleTransform) : Model {
    constructor(mesh: SmoothMesh) : this(mesh, SimpleTransform())
}