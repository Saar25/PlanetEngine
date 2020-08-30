package org.saar.lwjgl.opengl.objects.vbos;

import org.saar.lwjgl.opengl.constants.VboUsage;

import java.nio.ByteBuffer;

public final class Vbos {

    private Vbos() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }

    public static DataBuffer createDataBuffer(VboUsage usage, ByteBuffer buffer) {
        final DataBuffer vbo = new DataBuffer(usage);
        vbo.allocateByte(buffer.limit());
        vbo.storeData(0, buffer);
        return vbo;
    }

    public static IndexBuffer createIndexVbo(VboUsage usage, ByteBuffer buffer) {
        final IndexBuffer vbo = new IndexBuffer(usage);
        vbo.allocateByte(buffer.limit());
        vbo.storeData(0, buffer);
        return vbo;
    }

    public static void allocateAndStore(IVbo vbo, ByteBuffer buffer) {
        vbo.allocateByte(buffer.flip().limit());
        vbo.storeData(0, buffer);
    }

}
