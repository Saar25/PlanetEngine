package org.saar.minecraft.chunk

import org.saar.core.mesh.reader.VertexMeshReader
import org.saar.lwjgl.util.DataReader

class ChunkMeshReader(private val dataReader: DataReader) : VertexMeshReader<ChunkVertex> {

    override fun readVertex(): ChunkVertex {
        val data = this.dataReader.readInt()
        return Chunks.vertex(
            x = data shr 28 and 15,
            z = data shr 24 and 15,
            y = data shr 16 and 0xFF,
            blockId = data shr 8 and 0xFF,
            direction = data shr 5 and 7,
            light = data shr 1 and 0x0F,
            ao = data and 1 == 1
        )
    }
}