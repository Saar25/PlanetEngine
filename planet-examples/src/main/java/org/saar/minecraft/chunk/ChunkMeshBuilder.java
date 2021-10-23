package org.saar.minecraft.chunk;

import org.saar.core.mesh.builder.ArraysMeshBuilder;
import org.saar.core.mesh.builder.MeshBuilder;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.attributes.Attributes;

public class ChunkMeshBuilder implements MeshBuilder {

    private static final int[] indices = {0, 1, 2, 0, 2, 3};

    private static final int[][] vertexIds = {
            {5, 7, 3, 1}, // x+
            {2, 6, 4, 0}, // x-
            {4, 6, 7, 5}, // y+
            {1, 3, 2, 0}, // y-
            {3, 7, 6, 2}, // z+
            {0, 4, 5, 1}, // z-
    };

    private final ArraysMeshBuilder<ChunkVertex> builder;

    public ChunkMeshBuilder(ArraysMeshBuilder<ChunkVertex> builder, ChunkMeshPrototype prototype) {
        this.builder = builder;

        prototype.getDataBuffer().addAttribute(
                Attributes.ofInteger(0, 1, DataType.U_INT));
        this.builder.init();
    }

    public static ChunkMeshBuilder createDynamic() {
        return ChunkMeshBuilder.createDynamic(Chunks.meshPrototype());
    }

    public static ChunkMeshBuilder createDynamic(ChunkMeshPrototype prototype) {
        return new ChunkMeshBuilder(new ArraysMeshBuilder.Dynamic<>(
                prototype, new ChunkMeshWriter(prototype)), prototype);
    }

    public static ChunkMeshBuilder createFixed(int vertices) {
        return ChunkMeshBuilder.createFixed(Chunks.meshPrototype(), vertices);
    }

    public static ChunkMeshBuilder createFixed(ChunkMeshPrototype prototype, int vertices) {
        return new ChunkMeshBuilder(new ArraysMeshBuilder.Fixed<>(
                vertices, prototype, new ChunkMeshWriter(prototype)), prototype);
    }

    public void addBlock(int x, int y, int z, int id) {
        for (int i = 0; i < 6; i++) {
            addFace(x, y, z, id, i);
        }
    }

    public void addFace(int x, int y, int z, int id, int face) {
        final int[] faceIds = ChunkMeshBuilder.vertexIds[face];
        for (int index : ChunkMeshBuilder.indices) {
            final ChunkVertex vertex = Chunks.vertex(
                    x, y, z, id, faceIds[index], face == 0);
            this.builder.addVertex(vertex);
        }
    }

    @Override
    public ChunkMesh load() {
        return new ChunkMesh(this.builder.load());
    }
}
