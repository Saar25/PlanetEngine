package org.saar.minecraft.chunk

import org.saar.core.mesh.Vertex

interface ChunkVertex : Vertex {
    val x: Int
    val y: Int
    val z: Int
    val blockId: Int
    val vertexId: Int
    val textureInc: Boolean
}