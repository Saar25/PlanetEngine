package org.saar.core.common.obj;


import org.saar.core.mesh.DrawCallMesh;
import org.saar.core.mesh.Mesh;
import org.saar.core.mesh.build.MeshPrototypeHelper;
import org.saar.lwjgl.assimp.AssimpMesh;
import org.saar.lwjgl.assimp.AssimpUtil;
import org.saar.lwjgl.assimp.component.AssimpNormalComponent;
import org.saar.lwjgl.assimp.component.AssimpPositionComponent;
import org.saar.lwjgl.assimp.component.AssimpTexCoordComponent;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.drawcall.DrawCall;
import org.saar.lwjgl.opengl.drawcall.ElementsDrawCall;
import org.saar.lwjgl.opengl.objects.vaos.Vao;

public class ObjMesh implements Mesh {

    private final Mesh mesh;

    public ObjMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    static ObjMesh create(ObjMeshPrototype prototype, int indices) {
        final MeshPrototypeHelper helper = new MeshPrototypeHelper(prototype);

        final Vao vao = Vao.create();
        helper.loadToVao(vao);
        helper.store();

        final DrawCall drawCall = new ElementsDrawCall(
                RenderMode.TRIANGLES, indices, DataType.U_INT);
        final Mesh mesh = new DrawCallMesh(vao, drawCall);
        return new ObjMesh(mesh);
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
