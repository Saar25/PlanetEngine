package org.saar.core.common.obj;


import org.saar.core.mesh.DrawCallMesh;
import org.saar.core.mesh.Mesh;
import org.saar.core.mesh.Meshes;
import org.saar.core.mesh.build.MeshPrototypeHelper;

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

    public static ObjMesh load(String objFile) throws Exception {
        final ObjMeshPrototype prototype = Obj.mesh();
        ObjMeshBuilder.addAttributes(prototype);

        try (final ObjMeshLoader loader = new ObjMeshLoader(objFile)) {
            final MeshPrototypeHelper helper = new MeshPrototypeHelper(prototype);
            helper.allocateVertices(loader.vertexCount());
            helper.allocateIndices(loader.indexCount());

            final ObjMeshWriter writer = new ObjMeshWriter(prototype);
            writer.writeVertices(loader.loadVertices());
            writer.writeIndices(loader.loadIndices());

            return ObjMesh.create(prototype, loader.indexCount());
        }
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
