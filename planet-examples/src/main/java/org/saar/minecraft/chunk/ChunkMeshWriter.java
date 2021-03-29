package org.saar.minecraft.chunk;

import org.saar.core.mesh.build.writers.MeshVertexWriter;

public class ChunkMeshWriter implements MeshVertexWriter<ChunkVertex> {

    private final ChunkMeshPrototype prototype;

    public ChunkMeshWriter(ChunkMeshPrototype prototype) {
        this.prototype = prototype;
    }

    @Override
    public void writeVertex(ChunkVertex vertex) {
        final int data = (vertex.getX() & 0b11111) << 27
                | (vertex.getZ() & 0b11111) << 22
                | (vertex.getY() & 0x1FF) << 13
                | (vertex.getBlockId() & 0xFF) << 5
                | (vertex.getDirection() & 0b111) << 2;
        this.prototype.getDataBuffer().getWriter().write(data);
    }
}
