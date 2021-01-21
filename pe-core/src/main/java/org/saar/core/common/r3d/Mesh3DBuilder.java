package org.saar.core.common.r3d;

import org.saar.core.mesh.build.MeshBuilder;
import org.saar.core.mesh.build.MeshPrototypeHelper;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.attributes.Attribute;

public class Mesh3DBuilder implements MeshBuilder {

    private static final Attribute positionAttribute = Attribute.of(0, 3, DataType.FLOAT, true);

    private static final Attribute normalAttribute = Attribute.of(1, 3, DataType.FLOAT, true);

    private static final Attribute colourAttribute = Attribute.of(2, 3, DataType.FLOAT, true);

    private static final Attribute[] transformAttributes = {
            Attribute.ofInstance(3, 4, DataType.FLOAT, false),
            Attribute.ofInstance(4, 4, DataType.FLOAT, false),
            Attribute.ofInstance(5, 4, DataType.FLOAT, false),
            Attribute.ofInstance(6, 4, DataType.FLOAT, false)};

    private final Mesh3DPrototype prototype;
    private final Mesh3DWriter writer;

    private final int indices;
    private final int instances;

    public Mesh3DBuilder(Mesh3DPrototype prototype, int indices, int instances) {
        this.prototype = prototype;
        this.writer = new Mesh3DWriter(prototype);
        this.indices = indices;
        this.instances = instances;
    }

    private static void addAttributes(Mesh3DPrototype prototype) {
        prototype.getPositionBuffer().addAttribute(positionAttribute);
        prototype.getNormalBuffer().addAttribute(normalAttribute);
        prototype.getColourBuffer().addAttribute(colourAttribute);
        prototype.getTransformBuffer().addAttributes(transformAttributes);
    }

    private static void initPrototype(Mesh3DPrototype prototype, int vertices, int indices, int instances) {
        addAttributes(prototype);

        final MeshPrototypeHelper helper = new MeshPrototypeHelper(prototype);
        helper.allocateInstances(instances);
        helper.allocateVertices(vertices);
        helper.allocateIndices(indices);
    }

    public static Mesh3DBuilder create(Mesh3DPrototype prototype, int vertices, int indices, int instances) {
        Mesh3DBuilder.initPrototype(prototype, vertices, indices, instances);
        return new Mesh3DBuilder(prototype, indices, instances);
    }

    public static Mesh3DBuilder create(int vertices, int indices, int instances) {
        return Mesh3DBuilder.create(R3D.mesh(), vertices, indices, instances);
    }

    public static Mesh3DBuilder build(Mesh3DPrototype prototype, Vertex3D[] vertices, int[] indices, Instance3D[] instances) {
        final Mesh3DBuilder builder = create(prototype, vertices.length, indices.length, instances.length);
        builder.getWriter().writeInstances(instances);
        builder.getWriter().writeVertices(vertices);
        builder.getWriter().writeIndices(indices);
        return builder;
    }

    public static Mesh3DBuilder build(Vertex3D[] vertices, int[] indices, Instance3D[] instances) {
        return Mesh3DBuilder.build(R3D.mesh(), vertices, indices, instances);
    }

    public Mesh3DWriter getWriter() {
        return this.writer;
    }

    @Override
    public Mesh3D load() {
        return Mesh3D.create(this.prototype,
                this.indices, this.instances);
    }
}
