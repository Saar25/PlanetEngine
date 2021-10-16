package org.saar.minecraft.chunk;

import org.saar.core.mesh.build.MeshBuilder;
import org.saar.core.mesh.build.MeshPrototypeHelper;
import org.saar.core.mesh.build.writers.MeshVertexWriter;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.attributes.Attributes;
import org.saar.lwjgl.opengl.objects.attributes.IAttribute;

import java.util.ArrayList;
import java.util.List;

public abstract class ChunkMeshBuilder implements MeshBuilder, MeshVertexWriter<ChunkVertex> {

    private static final IAttribute dataAttribute = Attributes.ofInteger(0, 1, DataType.U_INT);

    private static final int[] indices = {0, 1, 2, 0, 2, 3};

    private static final int[][] vertexIds = {
            {5, 7, 3, 1}, // x+
            {2, 6, 4, 0}, // x-
            {4, 6, 7, 5}, // y+
            {1, 3, 2, 0}, // y-
            {3, 7, 6, 2}, // z+
            {0, 4, 5, 1}, // z-
    };

    static void addAttributes(ChunkMeshPrototype prototype) {
        prototype.getDataBuffer().addAttribute(dataAttribute);
    }

    private static void initPrototype(ChunkMeshPrototype prototype, int vertices) {
        addAttributes(prototype);

        final MeshPrototypeHelper helper = new MeshPrototypeHelper(prototype);
        helper.allocateVertices(vertices);
    }

    public static ChunkMeshBuilder createDynamic(ChunkMeshPrototype prototype) {
        return new ChunkMeshBuilder.DynamicChunkMeshBuilder(prototype);
    }

    public static ChunkMeshBuilder createFixed(ChunkMeshPrototype prototype, int vertices) {
        return new ChunkMeshBuilder.FixedChunkMeshBuilder(prototype, vertices);
    }

    public static ChunkMeshBuilder createDynamic() {
        return ChunkMeshBuilder.createDynamic(Chunks.meshPrototype());
    }

    public static ChunkMeshBuilder createFixed(int vertices) {
        return ChunkMeshBuilder.createFixed(Chunks.meshPrototype(), vertices);
    }

    public static ChunkMeshBuilder build(ChunkMeshPrototype prototype, ChunkVertex[] vertices) {
        final ChunkMeshBuilder builder = ChunkMeshBuilder.createFixed(prototype, vertices.length);
        builder.writeVertices(vertices);
        return builder;
    }

    public static ChunkMeshBuilder build(ChunkVertex[] vertices) {
        return ChunkMeshBuilder.build(Chunks.meshPrototype(), vertices);
    }

    public void writeBlock(int x, int y, int z, int id) {
        for (int i = 0; i < 6; i++) {
            writeFace(x, y, z, id, i);
        }
    }

    public void writeFace(int x, int y, int z, int id, int face) {
        final int[] faceIds = ChunkMeshBuilder.vertexIds[face];
        for (int index : ChunkMeshBuilder.indices) {
            writeVertex(Chunks.vertex(x, y, z, id, faceIds[index], face == 0));
        }
    }

    @Override
    public abstract ChunkMesh load();

    private static class FixedChunkMeshBuilder extends ChunkMeshBuilder {

        private final ChunkMeshPrototype prototype;
        private final ChunkMeshWriter writer;

        private final int vertices;

        public FixedChunkMeshBuilder(ChunkMeshPrototype prototype, int vertices) {
            this.prototype = prototype;
            this.writer = new ChunkMeshWriter(prototype);
            this.vertices = vertices;

            ChunkMeshBuilder.initPrototype(prototype, vertices);
        }

        @Override
        public void writeVertex(ChunkVertex vertex) {
            this.writer.writeVertex(vertex);
        }

        @Override
        public ChunkMesh load() {
            return ChunkMesh.create(this.prototype, this.vertices);
        }
    }

    private static class DynamicChunkMeshBuilder extends ChunkMeshBuilder {

        private final ChunkMeshPrototype prototype;

        private final List<ChunkVertex> vertices = new ArrayList<>();

        public DynamicChunkMeshBuilder(ChunkMeshPrototype prototype) {
            this.prototype = prototype;
        }

        @Override
        public void writeVertex(ChunkVertex vertex) {
            this.vertices.add(vertex);
        }

        @Override
        public ChunkMesh load() {
            initPrototype(this.prototype, this.vertices.size());
            final ChunkMeshWriter writer = new ChunkMeshWriter(this.prototype);

            writer.writeVertices(this.vertices);

            return ChunkMesh.create(this.prototype, this.vertices.size());
        }
    }
}
