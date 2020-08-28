package org.saar.core.model.loader;

import org.saar.lwjgl.opengl.objects.WriteableVbo;
import org.saar.lwjgl.opengl.utils.BufferWriter;
import org.saar.lwjgl.opengl.utils.MemoryUtils;

import java.nio.ByteBuffer;

public class ModelBuffer {

    private final WriteableVbo vbo;
    private final ByteBuffer buffer;
    private final BufferWriter writer;

    public ModelBuffer(WriteableVbo vbo, ByteBuffer buffer, BufferWriter writer) {
        this.vbo = vbo;
        this.buffer = buffer;
        this.writer = writer;
    }

    public static ModelBuffer allocate(WriteableVbo vbo, int bytes) {
        final ByteBuffer buffer = MemoryUtils.allocByte(bytes);
        final BufferWriter writer = new BufferWriter(buffer);
        return new ModelBuffer(vbo, buffer, writer);
    }

    public WriteableVbo getVbo() {
        return this.vbo;
    }

    public ByteBuffer getBuffer() {
        return this.buffer;
    }

    public BufferWriter getWriter() {
        return this.writer;
    }
}
