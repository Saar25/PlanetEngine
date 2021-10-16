package org.saar.core.common.r3d;

import org.saar.core.mesh.build.MeshBuilder;
import org.saar.core.mesh.build.MeshPrototypeHelper;
import org.saar.core.mesh.build.writers.MeshIndexWriter;
import org.saar.core.mesh.build.writers.MeshInstanceWriter;
import org.saar.core.mesh.build.writers.MeshVertexWriter;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.attributes.Attributes;
import org.saar.lwjgl.opengl.objects.attributes.IAttribute;

import java.util.ArrayList;
import java.util.List;

public abstract class Mesh3DBuilder implements MeshBuilder,
        MeshVertexWriter<Vertex3D>, MeshIndexWriter, MeshInstanceWriter<Instance3D> {

    private static final IAttribute positionAttribute = Attributes.of(0, 3, DataType.FLOAT, true);

    private static final IAttribute normalAttribute = Attributes.of(1, 3, DataType.FLOAT, true);

    private static final IAttribute colourAttribute = Attributes.of(2, 3, DataType.FLOAT, true);

    private static final IAttribute[] transformAttributes = {
            Attributes.ofInstanced(3, 4, DataType.FLOAT, false),
            Attributes.ofInstanced(4, 4, DataType.FLOAT, false),
            Attributes.ofInstanced(5, 4, DataType.FLOAT, false),
            Attributes.ofInstanced(6, 4, DataType.FLOAT, false)};

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

    public static Mesh3DBuilder create(Mesh3DPrototype prototype) {
        return new DynamicMesh3DBuilder(prototype);
    }

    public static Mesh3DBuilder create(Mesh3DPrototype prototype, int vertices, int indices, int instances) {
        return new FixedMesh3DBuilder(prototype, vertices, indices, instances);
    }

    public static Mesh3DBuilder build(Mesh3DPrototype prototype, Vertex3D[] vertices, int[] indices, Instance3D[] instances) {
        final Mesh3DBuilder builder = create(prototype,
                vertices.length, indices.length, instances.length);
        builder.writeIndices(indices);
        builder.writeVertices(vertices);
        builder.writeInstances(instances);
        return builder;
    }

    public static Mesh3DBuilder build(Vertex3D[] vertices, int[] indices, Instance3D[] instances) {
        return Mesh3DBuilder.build(R3D.mesh(), vertices, indices, instances);
    }

    @Override
    public abstract Mesh3D load();

    private static class FixedMesh3DBuilder extends Mesh3DBuilder {

        private final Mesh3DPrototype prototype;
        private final Mesh3DWriter writer;

        private final int indices;
        private final int instances;

        public FixedMesh3DBuilder(Mesh3DPrototype prototype, int vertices, int indices, int instances) {
            this.prototype = prototype;
            this.writer = new Mesh3DWriter(prototype);
            this.indices = indices;
            this.instances = instances;

            Mesh3DBuilder.initPrototype(prototype, vertices, indices, instances);
        }

        @Override
        public void writeIndex(int index) {
            this.writer.writeIndex(index);
        }

        @Override
        public void writeInstance(Instance3D instance) {
            this.writer.writeInstance(instance);
        }

        @Override
        public void writeVertex(Vertex3D vertex) {
            this.writer.writeVertex(vertex);
        }

        @Override
        public Mesh3D load() {
            return Mesh3D.create(this.prototype, this.indices, this.instances);
        }
    }

    private static class DynamicMesh3DBuilder extends Mesh3DBuilder {

        private final Mesh3DPrototype prototype;

        private final List<Integer> indices = new ArrayList<>();
        private final List<Vertex3D> vertices = new ArrayList<>();
        private final List<Instance3D> instances = new ArrayList<>();

        public DynamicMesh3DBuilder(Mesh3DPrototype prototype) {
            this.prototype = prototype;
        }

        @Override
        public void writeIndex(int index) {
            this.indices.add(index);
        }

        @Override
        public void writeInstance(Instance3D instance) {
            this.instances.add(instance);
        }

        @Override
        public void writeVertex(Vertex3D vertex) {
            this.vertices.add(vertex);
        }

        @Override
        public Mesh3D load() {
            initPrototype(this.prototype, this.vertices.size(), this.indices.size(), this.instances.size());
            final Mesh3DWriter writer = new Mesh3DWriter(this.prototype);

            writer.writeIndices(this.indices);
            writer.writeVertices(this.vertices);
            writer.writeInstances(this.instances);

            return Mesh3D.create(this.prototype, this.indices.size(), this.instances.size());
        }
    }
}
