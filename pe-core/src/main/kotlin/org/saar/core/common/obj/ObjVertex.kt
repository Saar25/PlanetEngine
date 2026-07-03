package org.saar.core.common.obj

import org.joml.Vector2fc
import org.joml.Vector3fc
import org.saar.core.mesh.Vertex

interface ObjVertex : Vertex {
    val position3f: Vector3fc
    val uvCoord2f: Vector2fc
    val normal3f: Vector3fc
}