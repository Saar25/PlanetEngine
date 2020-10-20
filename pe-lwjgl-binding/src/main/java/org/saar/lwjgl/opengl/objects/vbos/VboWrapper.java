package org.saar.lwjgl.opengl.objects.vbos;

import org.lwjgl.system.MemoryUtil;
import org.saar.lwjgl.opengl.utils.BufferWriter;

import java.nio.ByteBuffer;

public class VboWrapper {

    private final IVbo vbo;

    private ByteBuffer buffer = null;
    private BufferWriter writer = null;

    public VboWrapper(IVbo vbo) {
        this.vbo = vbo;
    }

    public void allocateMore(int capacity) {
        final int current = this.buffer == null
                ? 0 : this.buffer.capacity();
        allocate(capacity + current);
    }

    public void allocate(int capacity) {
        MemoryUtil.memFree(this.buffer);
        this.vbo.allocateByte(capacity);
        this.buffer = MemoryUtil.memAlloc(capacity);
        this.writer = new BufferWriter(this.buffer);
    }

    public void store(long offset) {
        this.buffer.flip();
        this.vbo.storeData(offset, this.buffer);
        MemoryUtil.memFree(this.buffer);
        this.buffer = null;
        this.writer = null;
    }

    public BufferWriter getWriter() {
        return this.writer;
    }
}
