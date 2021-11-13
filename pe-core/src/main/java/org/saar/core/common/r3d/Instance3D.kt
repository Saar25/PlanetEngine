package org.saar.core.common.r3d

import org.saar.core.mesh.Instance
import org.saar.maths.transform.Transform

interface Instance3D : Instance {
    val transform: Transform
}