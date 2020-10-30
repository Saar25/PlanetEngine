package org.saar.core.common.obj;

import org.saar.core.model.mesh.MeshBuilder;

public class ObjMeshBuilder implements MeshBuilder {

    private final ObjMeshPrototype prototype;
    private final ObjMeshWriter writer;

    private final int indices;

    public ObjMeshBuilder(ObjMeshPrototype prototype, int indices) {
        this.prototype = prototype;
        this.writer = new ObjMeshWriter(prototype);
        this.indices = indices;
    }

    public static ObjMeshBuilder create(ObjMeshPrototype prototype, int vertices, int indices) {
        ObjMesh.initPrototype(prototype, vertices, indices);
        return new ObjMeshBuilder(prototype, indices);
    }

    public static ObjMeshBuilder create(int vertices, int indices) {
        return ObjMeshBuilder.create(Obj.mesh(), vertices, indices);
    }

    public static ObjMeshBuilder build(ObjMeshPrototype prototype, ObjVertex[] vertices, int[] indices) {
        final ObjMeshBuilder builder = ObjMeshBuilder.create(prototype, vertices.length, indices.length);
        builder.getWriter().writeVertices(vertices);
        builder.getWriter().writeIndices(indices);
        return builder;
    }

    public static ObjMeshBuilder build(ObjVertex[] vertices, int[] indices) {
        return ObjMeshBuilder.build(Obj.mesh(), vertices, indices);
    }

    public ObjMeshWriter getWriter() {
        return this.writer;
    }

    @Override
    public ObjMesh load() {
        return ObjMesh.create(this.prototype, this.indices);
    }
}
