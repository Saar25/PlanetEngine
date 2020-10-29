package org.saar.core.common.smooth;

import org.saar.core.model.mesh.MeshBuilder;

public class SmoothMeshBuilder implements MeshBuilder {

    private final SmoothMeshPrototype prototype;
    private final SmoothMeshWriter writer;

    private final int indices;

    private SmoothMeshBuilder(SmoothMeshPrototype prototype, int indices) {
        this.prototype = prototype;
        this.writer = new SmoothMeshWriter(prototype);
        this.indices = indices;
    }

    public static SmoothMeshBuilder create(SmoothMeshPrototype prototype, int vertices, int indices) {
        SmoothMesh.initPrototype(prototype, vertices, indices);
        return new SmoothMeshBuilder(prototype, indices);
    }

    public static SmoothMeshBuilder create(int vertices, int indices) {
        return SmoothMeshBuilder.create(Smooth.mesh(), vertices, indices);
    }

    public static SmoothMeshBuilder build(SmoothMeshPrototype prototype, SmoothVertex[] vertices, int[] indices) {
        SmoothMesh.initPrototype(prototype, vertices.length, indices.length);
        final SmoothMeshBuilder builder = new SmoothMeshBuilder(prototype, indices.length);
        builder.getWriter().writeVertices(vertices);
        builder.getWriter().writeIndices(indices);
        return builder;
    }

    public static SmoothMeshBuilder build(SmoothVertex[] vertices, int[] indices) {
        return SmoothMeshBuilder.build(Smooth.mesh(), vertices, indices);
    }

    public SmoothMeshWriter getWriter() {
        return this.writer;
    }

    @Override
    public SmoothMesh load() {
        return SmoothMesh.create(this.prototype, this.indices);
    }
}
