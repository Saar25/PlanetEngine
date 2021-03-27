package org.saar.lwjgl.opengl.objects.vbos;

import java.nio.ByteBuffer;

public final class Vbos {

    private Vbos() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }

    public static DataBuffer createDataBuffer(VboUsage usage, ByteBuffer buffer) {
        final DataBuffer vbo = new DataBuffer(usage);
        vbo.allocate(buffer.limit());
        vbo.store(0, buffer);
        return vbo;
    }

    public static IndexBuffer createIndexVbo(VboUsage usage, ByteBuffer buffer) {
        final IndexBuffer vbo = new IndexBuffer(usage);
        vbo.allocate(buffer.limit());
        vbo.store(0, buffer);
        return vbo;
    }

    public static void allocateAndStore(IVbo vbo, ByteBuffer buffer) {
        vbo.allocate(buffer.limit());
        vbo.store(0, buffer);
    }

}
