package org.saar.core.common.r3d;

import org.saar.core.model.Mesh;
import org.saar.core.model.mesh.MeshBuilder;
import org.saar.core.model.mesh.MeshPrototypeHelper;

public class Mesh3DBuilder implements MeshBuilder {

    private final Mesh3DPrototype prototype;
    private final Mesh3DWriter writer;

    private final int indices;
    private final int instances;

    public Mesh3DBuilder(Mesh3DPrototype prototype, Mesh3DWriter writer, int indices, int instances) {
        this.prototype = prototype;
        this.writer = writer;
        this.indices = indices;
        this.instances = instances;
    }

    public static Mesh3DBuilder create(Mesh3DPrototype prototype, int vertices, int indices, int instances) {
        final MeshPrototypeHelper helper = new MeshPrototypeHelper(prototype);
        Mesh3D.addAttributes(prototype);
        helper.allocateInstances(instances);
        helper.allocateVertices(vertices);
        helper.allocateIndices(indices);

        final Mesh3DWriter writer = new Mesh3DWriter(prototype);
        return new Mesh3DBuilder(prototype, writer, indices, instances);
    }

    public static Mesh3DBuilder create(int vertices, int indices, int instances) {
        return Mesh3DBuilder.create(R3D.mesh(), vertices, indices, instances);
    }

    public Mesh3DWriter getWriter() {
        return this.writer;
    }

    @Override
    public Mesh load() {
        return Mesh3D.create(this.prototype,
                this.indices, this.instances);
    }
}
