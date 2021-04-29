package org.saar.core.common.obj;


import org.saar.core.mesh.DrawCallMesh;
import org.saar.core.mesh.Mesh;
import org.saar.core.mesh.Meshes;
import org.saar.lwjgl.assimp.AssimpMesh;
import org.saar.lwjgl.assimp.AssimpUtil;
import org.saar.lwjgl.assimp.component.AssimpNormalComponent;
import org.saar.lwjgl.assimp.component.AssimpPositionComponent;
import org.saar.lwjgl.assimp.component.AssimpTexCoordComponent;

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

        try (final AssimpMesh assimpMesh = AssimpUtil.load(objFile)) {
            assimpMesh.writeDataBuffer(
                    new AssimpPositionComponent(prototype.getPositionBuffer().getWrapper()),
                    new AssimpTexCoordComponent(0, prototype.getUvCoordBuffer().getWrapper()),
                    new AssimpNormalComponent(prototype.getNormalBuffer().getWrapper()));

            assimpMesh.writeIndexBuffer(prototype.getIndexBuffer().getWrapper());

            return ObjMesh.create(prototype, assimpMesh.indexCount());
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
