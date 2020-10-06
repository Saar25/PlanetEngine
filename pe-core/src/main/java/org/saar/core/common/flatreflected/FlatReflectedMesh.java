package org.saar.core.common.flatreflected;

import org.saar.core.model.Mesh;

public class FlatReflectedMesh implements Mesh {

    private final FlatReflectedModelBuffers buffers;

    private FlatReflectedMesh(FlatReflectedModelBuffers buffers) {
        this.buffers = buffers;
    }

    public static FlatReflectedMesh load(FlatReflectedVertex[] vertices, int[] indices) {
        final FlatReflectedModelBuffers buffers = FlatReflectedModelBuffers
                .singleModelBuffer(vertices.length, indices.length);
        buffers.load(vertices, indices);

        return new FlatReflectedMesh(buffers);
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
