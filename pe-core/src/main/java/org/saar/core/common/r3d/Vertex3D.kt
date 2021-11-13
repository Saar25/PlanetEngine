package org.saar.core.common.r3d

import org.joml.Vector3fc
import org.saar.core.mesh.Vertex

interface Vertex3D : Vertex {
    val position3f: Vector3fc
    val normal3f: Vector3fc
    val colour3f: Vector3fc
}