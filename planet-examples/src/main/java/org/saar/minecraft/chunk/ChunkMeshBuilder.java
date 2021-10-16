package org.saar.minecraft.chunk;

import org.saar.core.mesh.build.MeshBuilder;
import org.saar.core.mesh.build.MeshPrototypeHelper;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.attributes.Attributes;
import org.saar.lwjgl.opengl.objects.attributes.IAttribute;

public class ChunkMeshBuilder implements MeshBuilder {

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
        final int[] faceIds = ChunkMeshBuilder.vertexIds[face];
        for (int index : ChunkMeshBuilder.indices) {
            getWriter().writeVertex(Chunks.vertex(
                    x, y, z, id, faceIds[index], face == 0));
        }
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
