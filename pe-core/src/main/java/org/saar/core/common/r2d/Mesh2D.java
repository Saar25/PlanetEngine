package org.saar.core.common.r2d;

import org.saar.core.model.Mesh;

public class Mesh2D implements Mesh {

    private final ModelBuffers2D buffers;

    private Mesh2D(ModelBuffers2D buffers) {
        this.buffers = buffers;
    }

    public static Mesh2D load(Vertex2D[] vertices, int[] indices) {
        final ModelBuffers2D buffers = ModelBuffers2D.singleDataBuffer(
                vertices.length, indices.length);
        buffers.load(vertices, indices);
        return new Mesh2D(buffers);
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
