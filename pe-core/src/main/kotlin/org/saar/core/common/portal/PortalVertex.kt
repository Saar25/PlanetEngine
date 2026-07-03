package org.saar.core.common.portal

import org.joml.Vector3fc
import org.saar.core.mesh.Vertex

interface PortalVertex : Vertex {
    val position3f: Vector3fc
}