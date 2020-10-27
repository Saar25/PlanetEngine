package org.saar.core.common.r3d;

import org.saar.core.model.DrawCallMesh;
import org.saar.core.model.Mesh;
import org.saar.core.model.mesh.MeshPrototypeHelper;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.drawcall.DrawCall;
import org.saar.lwjgl.opengl.drawcall.InstancedElementsDrawCall;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.objects.vaos.Vao;

public class Mesh3D implements Mesh {

    private static final Attribute positionAttribute = Attribute.of(0, 3, DataType.FLOAT, true);

    private static final Attribute normalAttribute = Attribute.of(1, 3, DataType.FLOAT, true);

    private static final Attribute colourAttribute = Attribute.of(2, 3, DataType.FLOAT, true);

    private static final Attribute[] transformAttributes = {
            Attribute.ofInstance(3, 4, DataType.FLOAT, false),
            Attribute.ofInstance(4, 4, DataType.FLOAT, false),
            Attribute.ofInstance(5, 4, DataType.FLOAT, false),
            Attribute.ofInstance(6, 4, DataType.FLOAT, false)};

    private final Mesh mesh;

    private Mesh3D(Mesh mesh) {
        this.mesh = mesh;
    }

    private static void addAttributes(Mesh3DPrototype prototype) {
        prototype.getPositionBuffer().addAttribute(positionAttribute);
        prototype.getNormalBuffer().addAttribute(normalAttribute);
        prototype.getColourBuffer().addAttribute(colourAttribute);
        prototype.getTransformBuffer().addAttributes(transformAttributes);
    }

    static void initPrototype(Mesh3DPrototype prototype, int vertices, int indices, int instances) {
        addAttributes(prototype);

        final MeshPrototypeHelper helper = new MeshPrototypeHelper(prototype);
        helper.allocateInstances(instances);
        helper.allocateVertices(vertices);
        helper.allocateIndices(indices);
    }

    static Mesh3D create(Mesh3DPrototype prototype, int indices, int instances) {
        final MeshPrototypeHelper helper = new MeshPrototypeHelper(prototype);
        helper.store();

        final Vao vao = Vao.create();
        helper.loadToVao(vao);

        final DrawCall drawCall = new InstancedElementsDrawCall(
                RenderMode.TRIANGLES, indices, DataType.U_INT, instances);
        final Mesh mesh = new DrawCallMesh(vao, drawCall);
        return new Mesh3D(mesh);
    }

    public static Mesh3D load(Mesh3DPrototype prototype, Vertex3D[] vertices, int[] indices, Node3D[] instances) {
        final Mesh3DBuilder builder = Mesh3DBuilder.create(prototype,
                vertices.length, indices.length, instances.length);
        builder.getWriter().writeInstances(instances);
        builder.getWriter().writeVertices(vertices);
        builder.getWriter().writeIndices(indices);
        return builder.load();
    }

    public static Mesh3D load(Vertex3D[] vertices, int[] indices, Node3D[] instances) {
        return Mesh3D.load(R3D.mesh(), vertices, indices, instances);
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
