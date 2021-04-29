package org.saar.core.common.obj;

import org.saar.core.mesh.build.MeshBuilder;
import org.saar.core.mesh.build.MeshPrototypeHelper;
import org.saar.core.mesh.build.writers.MeshIndexWriter;
import org.saar.core.mesh.build.writers.MeshVertexWriter;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.attributes.Attribute;

import java.util.ArrayList;
import java.util.List;

public abstract class ObjMeshBuilder implements MeshBuilder, MeshVertexWriter<ObjVertex>, MeshIndexWriter {

    private static final Attribute positionAttribute = Attribute.of(0, 3, DataType.FLOAT, false);
    private static final Attribute uvCoordAttribute = Attribute.of(1, 2, DataType.FLOAT, false);
    private static final Attribute normalAttribute = Attribute.of(2, 3, DataType.FLOAT, false);

    static void addAttributes(ObjMeshPrototype prototype) {
        prototype.getPositionBuffer().addAttribute(positionAttribute);
        prototype.getUvCoordBuffer().addAttribute(uvCoordAttribute);
        prototype.getNormalBuffer().addAttribute(normalAttribute);
    }

    private static void initPrototype(ObjMeshPrototype prototype, int vertices, int indices) {
        addAttributes(prototype);

        final MeshPrototypeHelper helper = new MeshPrototypeHelper(prototype);
        helper.allocateVertices(vertices);
        helper.allocateIndices(indices);
    }

    public static ObjMeshBuilder create(ObjMeshPrototype prototype) {
        return new DynamicObjMeshBuilder(prototype);
    }

    public static ObjMeshBuilder create(ObjMeshPrototype prototype, int vertices, int indices) {
        return new FixedObjMeshBuilder(prototype, vertices, indices);
    }

    public static ObjMeshBuilder build(ObjMeshPrototype prototype, ObjVertex[] vertices, int[] indices) {
        final ObjMeshBuilder builder = ObjMeshBuilder.create(prototype, vertices.length, indices.length);
        builder.writeVertices(vertices);
        builder.writeIndices(indices);
        return builder;
    }

    public static ObjMeshBuilder build(ObjVertex[] vertices, int[] indices) {
        return ObjMeshBuilder.build(Obj.mesh(), vertices, indices);
    }

    @Override
    public abstract ObjMesh load();

    private static class FixedObjMeshBuilder extends ObjMeshBuilder {

        private final ObjMeshPrototype prototype;
        private final ObjMeshWriter writer;

        private final int indices;

        public FixedObjMeshBuilder(ObjMeshPrototype prototype, int vertices, int indices) {
            this.prototype = prototype;
            this.writer = new ObjMeshWriter(prototype);
            this.indices = indices;

            ObjMeshBuilder.initPrototype(prototype, vertices, indices);
        }

        @Override
        public void writeIndex(int index) {
            this.writer.writeIndex(index);
        }

        @Override
        public void writeVertex(ObjVertex vertex) {
            this.writer.writeVertex(vertex);
        }

        @Override
        public ObjMesh load() {
            return ObjMesh.create(this.prototype, this.indices);
        }
    }

    private static class DynamicObjMeshBuilder extends ObjMeshBuilder {

        private final ObjMeshPrototype prototype;

        private final List<Integer> indices = new ArrayList<>();
        private final List<ObjVertex> vertices = new ArrayList<>();

        public DynamicObjMeshBuilder(ObjMeshPrototype prototype) {
            this.prototype = prototype;
        }

        @Override
        public void writeIndex(int index) {
            this.indices.add(index);
        }

        @Override
        public void writeVertex(ObjVertex vertex) {
            this.vertices.add(vertex);
        }

        @Override
        public ObjMesh load() {
            initPrototype(this.prototype, this.vertices.size(), this.indices.size());
            final ObjMeshWriter writer = new ObjMeshWriter(this.prototype);

            writer.writeIndices(this.indices.stream().mapToInt(i -> i).toArray());
            writer.writeVertices(this.vertices.toArray(new ObjVertex[0]));

            return ObjMesh.create(this.prototype, this.indices.size());
        }
    }
}
