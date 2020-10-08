package org.saar.core.model.loader;

import org.saar.lwjgl.opengl.objects.vbos.IVbo;
import org.saar.lwjgl.opengl.utils.BufferWriter;
import org.saar.lwjgl.opengl.utils.MemoryUtils;

import java.nio.ByteBuffer;

public class ModelBuffer {

    private final IVbo vbo;
    private final BufferWriter writer;
    private final ByteBuffer buffer;

    public ModelBuffer(IVbo vbo, ByteBuffer buffer, BufferWriter writer) {
        this.vbo = vbo;
        this.buffer = buffer;
        this.writer = writer;
    }

    public static ModelBuffer allocate(IVbo vbo, int bytes) {
        final ByteBuffer buffer = MemoryUtils.allocByte(bytes);
        final BufferWriter writer = new BufferWriter(buffer);
        return new ModelBuffer(vbo, buffer, writer);
    }

    public IVbo getVbo() {
        return this.vbo;
    }

    public ByteBuffer getBuffer() {
        return this.buffer;
    }

    public BufferWriter getWriter() {
        return this.writer;
    }
}
