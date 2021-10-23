package org.saar.minecraft.chunk

import org.saar.core.mesh.MeshPrototype
import org.saar.core.mesh.buffer.MeshVertexBuffer

interface ChunkMeshPrototype : MeshPrototype {
    val dataBuffer: MeshVertexBuffer
}