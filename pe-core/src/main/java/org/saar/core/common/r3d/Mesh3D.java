package org.saar.core.common.r3d;

import org.saar.core.model.Mesh;

public class Mesh3D implements Mesh {

    private final Mesh mesh;

    private Mesh3D(Mesh mesh) {
        this.mesh = mesh;
    }

    public static Mesh3D load(Vertex3D[] vertices, int[] indices, Node3D[] instances) {
        final ModelBuffers3D buffers = ModelBuffers3D.singleModelBuffer();
        buffers.load(vertices, indices, instances);
        return new Mesh3D(buffers.getMesh());
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
