package org.saar.core.common.obj;


import org.saar.core.model.DrawCall;
import org.saar.core.model.DrawCallMesh;
import org.saar.core.model.ElementsDrawCall;
import org.saar.core.model.Mesh;
import org.saar.core.model.mesh.MeshPrototypeHelper;
import org.saar.core.model.mesh.MeshWriters;
import org.saar.lwjgl.assimp.AssimpMesh;
import org.saar.lwjgl.assimp.AssimpUtil;
import org.saar.lwjgl.assimp.component.AssimpNormalComponent;
import org.saar.lwjgl.assimp.component.AssimpPositionComponent;
import org.saar.lwjgl.assimp.component.AssimpTexCoordComponent;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.objects.vaos.Vao;

public class ObjMesh implements Mesh {

    private static final Attribute positionAttribute = Attribute.of(0, 3, DataType.FLOAT, false);
    private static final Attribute uvCoordAttribute = Attribute.of(1, 2, DataType.FLOAT, false);
    private static final Attribute normalAttribute = Attribute.of(2, 3, DataType.FLOAT, false);

    private final Mesh mesh;

    public ObjMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    private static void setUpPrototype(ObjMeshPrototype prototype) {
        prototype.getPositionBuffer().addAttribute(positionAttribute);
        prototype.getUvCoordBuffer().addAttribute(uvCoordAttribute);
        prototype.getNormalBuffer().addAttribute(normalAttribute);
    }

    public static ObjMesh load(ObjMeshPrototype prototype, ObjVertex[] vertices, int[] indices) {
        setUpPrototype(prototype);

        final MeshPrototypeHelper helper = new MeshPrototypeHelper(prototype);

        final Vao vao = Vao.create();
        helper.loadToVao(vao);
        helper.allocateIndices(indices);
        helper.allocateVertices(vertices);

        final ObjMeshWriter writer = new ObjMeshWriter(prototype);
        MeshWriters.writeVertices(writer, vertices);
        MeshWriters.writeIndices(writer, indices);

        helper.store();

        final DrawCall drawCall = new ElementsDrawCall(
                RenderMode.TRIANGLES, indices.length, DataType.U_INT);
        final Mesh mesh = new DrawCallMesh(vao, drawCall);
        return new ObjMesh(mesh);
    }

    public static ObjMesh load(ObjVertex[] vertices, int[] indices) {
        return ObjMesh.load(Obj.mesh(), vertices, indices);
    }

    public static ObjMesh load(String objFile) throws Exception {
        final ObjMeshPrototype prototype = Obj.mesh();
        setUpPrototype(prototype);

        final MeshPrototypeHelper helper = new MeshPrototypeHelper(prototype);

        final Vao vao = Vao.create();

        try (final AssimpMesh assimpMesh = AssimpUtil.load(objFile)) {
            assimpMesh.writeDataBuffer(
                    new AssimpPositionComponent(prototype.getPositionBuffer().getWrapper()),
                    new AssimpTexCoordComponent(0, prototype.getUvCoordBuffer().getWrapper()),
                    new AssimpNormalComponent(prototype.getNormalBuffer().getWrapper()));

            assimpMesh.writeIndexBuffer(prototype.getIndexBuffer().getWrapper());

            helper.store();
            helper.loadToVao(vao);

            final DrawCall drawCall = new ElementsDrawCall(
                    RenderMode.TRIANGLES, assimpMesh.indexCount(), DataType.U_INT);
            final Mesh mesh = new DrawCallMesh(vao, drawCall);
            return new ObjMesh(mesh);
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
