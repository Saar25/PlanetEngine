package org.saar.lwjgl.opengl.objects;

import org.saar.lwjgl.opengl.constants.VboUsage;

import java.nio.ByteBuffer;

public final class Vbos {

    private Vbos() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }

    public DataBuffer createDataBuffer(VboUsage usage, ByteBuffer buffer) {
        final DataBuffer vbo = new DataBuffer(usage);
        vbo.allocateByte(buffer.limit());
        vbo.storeData(0, buffer);
        return vbo;
    }

    public IndexBuffer createIndexVbo(VboUsage usage, ByteBuffer buffer) {
        final IndexBuffer vbo = new IndexBuffer(usage);
        vbo.allocateByte(buffer.limit());
        vbo.storeData(0, buffer);
        return vbo;
    }

}
