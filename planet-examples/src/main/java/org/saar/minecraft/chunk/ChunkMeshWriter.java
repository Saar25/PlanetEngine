package org.saar.minecraft.chunk;

import org.saar.core.mesh.build.writers.MeshVertexWriter;

public class ChunkMeshWriter implements MeshVertexWriter<ChunkVertex> {

    private final ChunkMeshPrototype prototype;

    public ChunkMeshWriter(ChunkMeshPrototype prototype) {
        this.prototype = prototype;
    }

    @Override
    public void writeVertex(ChunkVertex vertex) {
        final int data = (vertex.getX() & 0b1111) << 28
                | (vertex.getZ() & 0b1111) << 24
                | (vertex.getY() & 0xFF) << 16
                | (vertex.getBlockId() & 0xFF) << 8
                | (vertex.getVertexId() & 0b111) << 5
                | (vertex.getTextureInc() ? 1 : 0) << 4;
        this.prototype.getDataBuffer().getWriter().writeInt(data);
    }
}
