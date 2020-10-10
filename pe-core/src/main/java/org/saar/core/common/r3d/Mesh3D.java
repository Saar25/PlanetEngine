package org.saar.core.common.r3d;

import org.saar.core.model.InstancedElementsMesh;
import org.saar.core.model.Mesh;
import org.saar.core.model.loader.ModelWriters;
import org.saar.core.model.mesh.MeshPrototypeHelper;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
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

    public static Mesh3D load(Mesh3DPrototype prototype, Vertex3D[] vertices, int[] indices, Node3D[] instances) {
        prototype.getPositionBuffer().addAttribute(positionAttribute);
        prototype.getNormalBuffer().addAttribute(normalAttribute);
        prototype.getColourBuffer().addAttribute(colourAttribute);
        prototype.getTransformBuffer().addAttributes(transformAttributes);

        final MeshPrototypeHelper helper = new MeshPrototypeHelper(prototype);

        final Vao vao = Vao.create();
        helper.loadToVao(vao);
        helper.allocateIndices(indices);
        helper.allocateVertices(vertices);
        helper.allocateInstances(instances);

        final Mesh3DWriter writer = new Mesh3DWriter(prototype);
        ModelWriters.writeVertices(writer, vertices);
        ModelWriters.writeIndices(writer, indices);
        ModelWriters.writeNodes(writer, instances);

        helper.store();

        final Mesh mesh = new InstancedElementsMesh(vao,
                RenderMode.TRIANGLES, indices.length, DataType.U_INT, instances.length);
        return new Mesh3D(mesh);
    }

    public static Mesh3D load(Vertex3D[] vertices, int[] indices, Node3D[] instances) {
        final Mesh3DPrototype prototype = new Mesh3DPrototypeImpl();
        return Mesh3D.load(prototype, vertices, indices, instances);
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
