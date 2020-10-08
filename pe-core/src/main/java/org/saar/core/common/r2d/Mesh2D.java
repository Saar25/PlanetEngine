package org.saar.core.common.r2d;

import org.saar.core.model.Mesh;

public class Mesh2D implements Mesh {

    private final Mesh mesh;

    private Mesh2D(Mesh mesh) {
        this.mesh = mesh;
    }

    public static Mesh2D load(Vertex2D[] vertices, int[] indices) {
        final ModelBuffers2D buffers = ModelBuffers2D.singleDataBuffer(
                vertices.length, indices.length);
        buffers.load(vertices, indices);
        return new Mesh2D(buffers.getMesh());
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
