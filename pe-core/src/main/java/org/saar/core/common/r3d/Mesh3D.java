package org.saar.core.common.r3d;

import org.saar.core.model.Mesh;

public class Mesh3D implements Mesh {

    private final ModelBuffers3D buffers;

    private Mesh3D(ModelBuffers3D buffers) {
        this.buffers = buffers;
    }

    public static Mesh3D load(Vertex3D[] vertices, int[] indices, Node3D[] instances) {
        final ModelBuffers3D buffers = ModelBuffers3D.singleModelBuffer(
                vertices.length, indices.length, instances.length);
        buffers.load(vertices, indices, instances);
        return new Mesh3D(buffers);
    }

    @Override
    public void draw() {
        this.buffers.getMesh().draw();
    }

    @Override
    public void delete() {
        this.buffers.getMesh().delete();
    }
}
