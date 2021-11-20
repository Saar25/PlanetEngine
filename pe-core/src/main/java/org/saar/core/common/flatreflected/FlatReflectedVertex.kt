package org.saar.core.common.flatreflected

import org.joml.Vector3fc
import org.saar.core.mesh.Vertex

interface FlatReflectedVertex : Vertex {
    val position3f: Vector3fc
}