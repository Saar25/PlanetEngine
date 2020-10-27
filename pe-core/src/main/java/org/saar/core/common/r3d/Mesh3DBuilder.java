package org.saar.core.common.r3d;

import org.saar.core.model.mesh.MeshBuilder;

public class Mesh3DBuilder implements MeshBuilder {

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

    public static Mesh3DBuilder create(Mesh3DPrototype prototype, int vertices, int indices, int instances) {
        Mesh3D.initPrototype(prototype, vertices, indices, instances);
        return new Mesh3DBuilder(prototype, indices, instances);
    }

    public static Mesh3DBuilder create(int vertices, int indices, int instances) {
        return Mesh3DBuilder.create(R3D.mesh(), vertices, indices, instances);
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
