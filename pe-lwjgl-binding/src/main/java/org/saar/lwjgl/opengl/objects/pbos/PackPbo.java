package org.saar.lwjgl.opengl.objects.pbos;

import org.lwjgl.opengl.GL21;
import org.saar.lwjgl.opengl.constants.*;
import org.saar.lwjgl.opengl.objects.vbos.Vbo;

import java.nio.ByteBuffer;

public class PackPbo implements ReadablePbo {

    private final Vbo buffer;

    private PackPbo(Vbo buffer) {
        this.buffer = buffer;
    }

    public static PackPbo create() {
        final Vbo buffer = Vbo.create(
                VboTarget.PACK_BUFFER,
                VboUsage.STATIC_DRAW);
        return new PackPbo(buffer);
    }

    @Override
    public void readPixels(int x, int y, int width, int height, InternalFormat format, DataType dataType) {
        this.buffer.bind();
        GL21.glReadPixels(x, y, width, height, format.get(), dataType.get(), 0);
    }

    public ByteBuffer map(VboAccess access) {
        return this.buffer.map(access);
    }

    public void unmap() {
        this.buffer.unmap();
    }

    @Override
    public void delete() {
        this.buffer.delete();
    }
}
