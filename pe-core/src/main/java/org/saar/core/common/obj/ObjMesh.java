package org.saar.core.common.obj;

import org.saar.core.mesh.DrawCallMesh;
import org.saar.core.mesh.Mesh;
import org.saar.core.mesh.Meshes;

import java.util.Collection;

public class ObjMesh implements Mesh {

    private final DrawCallMesh mesh;

    public ObjMesh(DrawCallMesh mesh) {
        this.mesh = mesh;
    }

    static ObjMesh create(ObjMeshPrototype prototype, int indices) {
        return new ObjMesh(Meshes.toElementsDrawCallMesh(prototype, indices));
    }

    public static ObjMesh load(ObjMeshPrototype prototype, ObjVertex[] vertices, int[] indices) {
        return ObjMeshBuilder.build(prototype, vertices, indices).load();
    }

    public static ObjMesh load(ObjVertex[] vertices, int[] indices) {
        return ObjMeshBuilder.build(Obj.mesh(), vertices, indices).load();
    }

    private static ObjMesh load(ObjMeshPrototype prototype, Collection<ObjVertex> vertices, Collection<Integer> indices) {
        final ObjMeshBuilder builder = ObjMeshBuilder.create(
                prototype, vertices.size(), indices.size());
        builder.writeVertices(vertices);
        builder.writeIndices(indices);
        return builder.load();
    }

    private static ObjMesh load(Collection<ObjVertex> vertices, Collection<Integer> indices) {
        return load(Obj.mesh(), vertices, indices);
    }

    public static ObjMesh load(ObjMeshPrototype prototype, String objFile) throws Exception {
        try (final ObjMeshLoader loader = new ObjMeshLoader(objFile)) {
            return load(prototype, loader.loadVertices(), loader.loadIndices());
        }
    }

    public static ObjMesh load(String objFile) throws Exception {
        return load(Obj.mesh(), objFile);
    }

    @Override
    public void draw() {
        this.mesh.draw();
    }

    @Override
    public void delete() {
        this.mesh.delete();
    }
}
