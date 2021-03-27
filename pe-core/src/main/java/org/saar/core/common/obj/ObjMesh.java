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
import org.saar.lwjgl.opengl.objects.attributes.Attribute;
import org.saar.lwjgl.opengl.objects.vaos.Vao;

public class ObjMesh implements Mesh {

    private static final Attribute positionAttribute = Attribute.of(0, 3, DataType.FLOAT, false);
    private static final Attribute uvCoordAttribute = Attribute.of(1, 2, DataType.FLOAT, false);
    private static final Attribute normalAttribute = Attribute.of(2, 3, DataType.FLOAT, false);

    private final Mesh mesh;

    public ObjMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    private static void addAttributes(ObjMeshPrototype prototype) {
        prototype.getPositionBuffer().addAttribute(positionAttribute);
        prototype.getUvCoordBuffer().addAttribute(uvCoordAttribute);
        prototype.getNormalBuffer().addAttribute(normalAttribute);
    }

    static void initPrototype(ObjMeshPrototype prototype, int vertices, int indices) {
        addAttributes(prototype);
        final MeshPrototypeHelper helper = new MeshPrototypeHelper(prototype);
        helper.allocateVertices(vertices);
        helper.allocateIndices(indices);
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
        addAttributes(prototype);

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
