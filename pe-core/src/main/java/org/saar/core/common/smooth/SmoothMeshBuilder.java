package org.saar.core.common.smooth;

import org.saar.core.mesh.build.MeshBuilder;
import org.saar.core.mesh.build.MeshPrototypeHelper;
import org.saar.core.mesh.build.writers.MeshIndexWriter;
import org.saar.core.mesh.build.writers.MeshVertexWriter;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.attributes.Attribute;

import java.util.ArrayList;
import java.util.List;

public abstract class SmoothMeshBuilder implements MeshBuilder, MeshVertexWriter<SmoothVertex>, MeshIndexWriter {

    private static final Attribute positionAttribute = Attribute.of(0, 3, DataType.FLOAT, true);

    private static final Attribute normalAttribute = Attribute.of(1, 3, DataType.FLOAT, true);

    private static final Attribute colourAttribute = Attribute.of(2, 3, DataType.FLOAT, true);

    private static final Attribute targetAttribute = Attribute.of(3, 3, DataType.FLOAT, true);

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

    public static SmoothMeshBuilder create(SmoothMeshPrototype prototype) {
        return new DynamicSmoothMeshBuilder(prototype);
    }

    public static SmoothMeshBuilder create(SmoothMeshPrototype prototype, int vertices, int indices) {
        return new FixedSmoothMeshBuilder(prototype, vertices, indices);
    }

    public static SmoothMeshBuilder build(SmoothMeshPrototype prototype, SmoothVertex[] vertices, int[] indices) {
        final SmoothMeshBuilder builder = create(prototype, vertices.length, indices.length);
        builder.writeVertices(vertices);
        builder.writeIndices(indices);
        return builder;
    }

    public static SmoothMeshBuilder build(SmoothVertex[] vertices, int[] indices) {
        return SmoothMeshBuilder.build(Smooth.mesh(), vertices, indices);
    }

    @Override
    public abstract SmoothMesh load();

    private static class FixedSmoothMeshBuilder extends SmoothMeshBuilder {

        private final SmoothMeshPrototype prototype;
        private final SmoothMeshWriter writer;

        private final int indices;

        public FixedSmoothMeshBuilder(SmoothMeshPrototype prototype, int vertices, int indices) {
            this.prototype = prototype;
            this.writer = new SmoothMeshWriter(prototype);
            this.indices = indices;

            SmoothMeshBuilder.initPrototype(prototype, vertices, indices);
        }

        @Override
        public void writeIndex(int index) {
            this.writer.writeIndex(index);
        }

        @Override
        public void writeVertex(SmoothVertex vertex) {
            this.writer.writeVertex(vertex);
        }

        @Override
        public SmoothMesh load() {
            return SmoothMesh.create(this.prototype, this.indices);
        }
    }

    private static class DynamicSmoothMeshBuilder extends SmoothMeshBuilder {

        private final SmoothMeshPrototype prototype;

        private final List<Integer> indices = new ArrayList<>();
        private final List<SmoothVertex> vertices = new ArrayList<>();

        public DynamicSmoothMeshBuilder(SmoothMeshPrototype prototype) {
            this.prototype = prototype;
        }

        @Override
        public void writeIndex(int index) {
            this.indices.add(index);
        }

        @Override
        public void writeVertex(SmoothVertex vertex) {
            this.vertices.add(vertex);
        }

        @Override
        public SmoothMesh load() {
            initPrototype(this.prototype, this.vertices.size(), this.indices.size());
            final SmoothMeshWriter writer = new SmoothMeshWriter(this.prototype);

            writer.writeIndices(this.indices.stream().mapToInt(i -> i).toArray());
            writer.writeVertices(this.vertices.toArray(new SmoothVertex[0]));

            return SmoothMesh.create(this.prototype, this.indices.size());
        }
    }
}
