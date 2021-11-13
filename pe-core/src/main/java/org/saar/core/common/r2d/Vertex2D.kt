package org.saar.core.common.r2d

import org.joml.Vector2fc
import org.joml.Vector3fc
import org.saar.core.mesh.Vertex

interface Vertex2D : Vertex {
    val position2f: Vector2fc
    val colour3f: Vector3fc
}