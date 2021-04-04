package org.saar.minecraft.chunk;

import org.saar.core.mesh.build.MeshBuilder;
import org.saar.core.mesh.build.MeshPrototypeHelper;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.attributes.Attribute;

public class ChunkMeshBuilder implements MeshBuilder {

    private static final Attribute dataAttribute = Attribute.ofInteger(0, 1, DataType.U_INT);

    private static final int[][] facesOffsets = {
            {1, 1, 0}, {1, 1, 1}, {1, 0, 1}, {1, 0, 0}, // x+
            {0, 0, 1}, {0, 1, 1}, {0, 1, 0}, {0, 0, 0}, // x-
            {0, 1, 0}, {0, 1, 1}, {1, 1, 1}, {1, 1, 0}, // y+
            {1, 0, 0}, {1, 0, 1}, {0, 0, 1}, {0, 0, 0}, // y-
            {1, 0, 1}, {1, 1, 1}, {0, 1, 1}, {0, 0, 1}, // z+
            {0, 0, 0}, {0, 1, 0}, {1, 1, 0}, {1, 0, 0}, // z-
    };

    private static final int[][] vertexIds = {
            {5, 7, 3, 1}, // x+
            {2, 6, 4, 0}, // x-
            {4, 6, 7, 5}, // y+
            {1, 3, 2, 0}, // y-
            {3, 7, 6, 2}, // z+
            {0, 4, 5, 1}, // z-
    };

    private final ChunkMeshPrototype prototype;
    private final ChunkMeshWriter writer;
    private final int vertices;

    public ChunkMeshBuilder(ChunkMeshPrototype prototype, int vertices) {
        this.prototype = prototype;
        this.writer = new ChunkMeshWriter(prototype);
        this.vertices = vertices;
    }

    private static void addAttributes(ChunkMeshPrototype prototype) {
        prototype.getDataBuffer().addAttributes(dataAttribute);
    }

    private static void initPrototype(ChunkMeshPrototype prototype, int vertices) {
        addAttributes(prototype);

        final MeshPrototypeHelper helper = new MeshPrototypeHelper(prototype);
        helper.allocateVertices(vertices);
    }

    public static ChunkMeshBuilder create(ChunkMeshPrototype prototype, int vertices) {
        ChunkMeshBuilder.initPrototype(prototype, vertices);
        return new ChunkMeshBuilder(prototype, vertices);
    }

    public static ChunkMeshBuilder create(int vertices) {
        return ChunkMeshBuilder.create(Chunks.meshPrototype(), vertices);
    }

    public static ChunkMeshBuilder build(ChunkMeshPrototype prototype, ChunkVertex[] vertices) {
        final ChunkMeshBuilder builder = create(prototype, vertices.length);
        builder.getWriter().writeVertices(vertices);
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
        final int index = face * 4;
        final int[] v0 = facesOffsets[index], v1 = facesOffsets[index + 1],
                v2 = facesOffsets[index + 2], v3 = facesOffsets[index + 3];
        final int[] faceIds = vertexIds[face];
        getWriter().writeVertex(Chunks.vertex(x + v0[0], y + v0[1], z + v0[2], id, face, faceIds[0]));
        getWriter().writeVertex(Chunks.vertex(x + v1[0], y + v1[1], z + v1[2], id, face, faceIds[1]));
        getWriter().writeVertex(Chunks.vertex(x + v2[0], y + v2[1], z + v2[2], id, face, faceIds[2]));
        getWriter().writeVertex(Chunks.vertex(x + v0[0], y + v0[1], z + v0[2], id, face, faceIds[0]));
        getWriter().writeVertex(Chunks.vertex(x + v2[0], y + v2[1], z + v2[2], id, face, faceIds[2]));
        getWriter().writeVertex(Chunks.vertex(x + v3[0], y + v3[1], z + v3[2], id, face, faceIds[3]));
    }

    public ChunkMeshWriter getWriter() {
        return this.writer;
    }

    @Override
    public ChunkMesh load() {
        return ChunkMesh.create(this.prototype, this.vertices);
    }

    public void delete() {
        load().delete();
    }
}
