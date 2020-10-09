package org.saar.lwjgl.opengl.objects.pbos;

import org.lwjgl.opengl.GL21;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.InternalFormat;
import org.saar.lwjgl.opengl.objects.buffers.BufferAccess;
import org.saar.lwjgl.opengl.objects.buffers.BufferObject;
import org.saar.lwjgl.opengl.objects.buffers.BufferTarget;

import java.nio.ByteBuffer;

public class PackPbo implements ReadablePbo {

    private final BufferObject buffer;

    private PackPbo(BufferObject buffer) {
        this.buffer = buffer;
    }

    public static PackPbo create() {
        final BufferObject buffer = BufferObject.create();
        return new PackPbo(buffer);
    }

    @Override
    public void readPixels(int x, int y, int width, int height, InternalFormat format, DataType dataType) {
        this.buffer.bind(BufferTarget.PACK);
        GL21.glReadPixels(x, y, width, height, format.get(), dataType.get(), 0);
    }

    public ByteBuffer map(BufferAccess access) {
        return this.buffer.map(BufferTarget.PACK, access);
    }

    public void unmap() {
        this.buffer.unmap(BufferTarget.PACK);
    }

    @Override
    public void delete() {
        this.buffer.delete();
    }
}
