package org.saar.lwjgl.opengl.objects.buffers;

import org.lwjgl.system.MemoryUtil;
import org.saar.lwjgl.util.buffer.BufferWriter;

import java.nio.ByteBuffer;

public class BufferObjectWrapper {

    private final WritableBufferObject bufferObject;

    private ByteBuffer buffer = null;
    private BufferWriter writer = null;

    public BufferObjectWrapper(WritableBufferObject bufferObject) {
        this.bufferObject = bufferObject;
    }

    public void allocateMore(int capacity) {
        final int current = this.buffer == null
                ? 0 : this.buffer.capacity();
        allocate(capacity + current);
    }

    public void allocate(int capacity) {
        MemoryUtil.memFree(this.buffer);
        this.bufferObject.allocate(capacity);
        this.buffer = MemoryUtil.memAlloc(capacity);
        this.writer = new BufferWriter(this.buffer);
    }

    public void store(long offset) {
        this.buffer.flip();
        this.bufferObject.store(offset, this.buffer);
        MemoryUtil.memFree(this.buffer);
        this.buffer = null;
        this.writer = null;
    }

    public BufferWriter getWriter() {
        return this.writer;
    }
}
