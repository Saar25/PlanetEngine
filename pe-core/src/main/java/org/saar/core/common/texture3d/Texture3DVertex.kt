package org.saar.core.common.texture3d

import org.joml.Vector2fc
import org.joml.Vector3fc
import org.saar.core.mesh.Vertex

interface Texture3DVertex : Vertex {
    val position3f: Vector3fc
    val uvCoord2f: Vector2fc
}