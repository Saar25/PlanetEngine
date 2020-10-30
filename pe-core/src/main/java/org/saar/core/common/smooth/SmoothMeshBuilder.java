package org.saar.core.common.smooth;

import org.saar.core.mesh.build.MeshBuilder;
import org.saar.core.mesh.build.MeshPrototypeHelper;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.Attribute;

public class SmoothMeshBuilder implements MeshBuilder {

    private static final Attribute positionAttribute = Attribute.of(0, 3, DataType.FLOAT, true);

    private static final Attribute normalAttribute = Attribute.of(1, 3, DataType.FLOAT, true);

    private static final Attribute colourAttribute = Attribute.of(2, 3, DataType.FLOAT, true);

    private static final Attribute targetAttribute = Attribute.of(3, 3, DataType.FLOAT, true);

    private final SmoothMeshPrototype prototype;
    private final SmoothMeshWriter writer;

    private final int indices;

    private SmoothMeshBuilder(SmoothMeshPrototype prototype, int indices) {
        this.prototype = prototype;
        this.writer = new SmoothMeshWriter(prototype);
        this.indices = indices;
    }

    private static void addAttributes(SmoothMeshPrototype prototype) {
        prototype.getPositionBuffer().addAttribute(positionAttribute);
        prototype.getNormalBuffer().addAttribute(normalAttribute);
        prototype.getColourBuffer().addAttribute(colourAttribute);
        prototype.getTargetBuffer().addAttributes(targetAttribute);
    }

    private static void initPrototype(SmoothMeshPrototype prototype, int vertices, int indices) {
        addAttributes(prototype);

        final MeshPrototypeHelper helper = new MeshPrototypeHelper(prototype);
        helper.allocateVertices(vertices);
        helper.allocateIndices(indices);
    }

    public static SmoothMeshBuilder create(SmoothMeshPrototype prototype, int vertices, int indices) {
        SmoothMeshBuilder.initPrototype(prototype, vertices, indices);
        return new SmoothMeshBuilder(prototype, indices);
    }

    public static SmoothMeshBuilder create(int vertices, int indices) {
        return SmoothMeshBuilder.create(Smooth.mesh(), vertices, indices);
    }

    public static SmoothMeshBuilder build(SmoothMeshPrototype prototype, SmoothVertex[] vertices, int[] indices) {
        final SmoothMeshBuilder builder = create(prototype, vertices.length, indices.length);
        builder.getWriter().writeVertices(vertices);
        builder.getWriter().writeIndices(indices);
        return builder;
    }

    public static SmoothMeshBuilder build(SmoothVertex[] vertices, int[] indices) {
        return SmoothMeshBuilder.build(Smooth.mesh(), vertices, indices);
    }

    public SmoothMeshWriter getWriter() {
        return this.writer;
    }

    @Override
    public SmoothMesh load() {
        return SmoothMesh.create(this.prototype, this.indices);
    }
}
