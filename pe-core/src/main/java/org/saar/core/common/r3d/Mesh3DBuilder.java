package org.saar.core.common.r3d;

import org.saar.core.model.Mesh;
import org.saar.core.model.mesh.MeshBuilder;
import org.saar.core.model.mesh.MeshPrototypeHelper;

public class Mesh3DBuilder implements MeshBuilder {

    private final Mesh3DWriter writer;
    private final MeshPrototypeHelper helper;
    private final Mesh3D mesh;

    public Mesh3DBuilder(Mesh3DWriter writer, MeshPrototypeHelper helper, Mesh3D mesh) {
        this.writer = writer;
        this.helper = helper;
        this.mesh = mesh;
    }

    public static Mesh3DBuilder create(Mesh3DPrototype prototype, int vertices, int indices, int instances) {
        final Mesh3DWriter writer = new Mesh3DWriter(prototype);
        final MeshPrototypeHelper helper = new MeshPrototypeHelper(prototype);
        final Mesh3D mesh = Mesh3D.create(prototype, indices, instances);
        helper.allocateInstances(instances);
        helper.allocateVertices(vertices);
        helper.allocateIndices(indices);
        return new Mesh3DBuilder(writer, helper, mesh);
    }

    public static Mesh3DBuilder create(int vertices, int indices, int instances) {
        return Mesh3DBuilder.create(R3D.mesh(), vertices, indices, instances);
    }

    public void writeVertex(Vertex3D vertex) {
        this.writer.writeVertex(vertex);
    }

    public void writeVertices(Vertex3D... vertices) {
        this.writer.writeVertices(vertices);
    }

    public void writeInstance(Node3D instance) {
        this.writer.writeNode(instance);
    }

    public void writeInstance(Node3D... instances) {
        this.writer.writeInstances(instances);
    }

    public void writeIndex(int index) {
        this.writer.writeIndex(index);
    }

    public void writeIndices(int... indices) {
        this.writer.writeIndices(indices);
    }

    @Override
    public void finishBuilding() {
        this.helper.store();
    }

    @Override
    public Mesh asMesh() {
        return this.mesh;
    }
}
