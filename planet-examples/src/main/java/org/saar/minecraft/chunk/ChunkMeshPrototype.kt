package org.saar.minecraft.chunk

import org.saar.core.mesh.build.MeshPrototype
import org.saar.core.mesh.build.buffers.MeshVertexBuffer

interface ChunkMeshPrototype : MeshPrototype {
    val dataBuffer: MeshVertexBuffer
}