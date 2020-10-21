package org.saar.lwjgl.opengl.objects.vbos;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.buffers.BufferObject;
import org.saar.lwjgl.opengl.objects.buffers.BufferTarget;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class IndexBuffer implements IVbo {

    private static final BufferTarget TARGET = BufferTarget.ELEMENT_ARRAY;

    public static final IndexBuffer NULL = new IndexBuffer(BufferObject.NULL, VboUsage.STATIC_DRAW);

    private final BufferObject buffer;
    private final VboUsage usage;

    private IndexBuffer(BufferObject buffer, VboUsage usage) {
        this.buffer = buffer;
        this.usage = usage;
    }

    public IndexBuffer(VboUsage usage) {
        this(BufferObject.create(), usage);
    }

    private BufferObject getBuffer() {
        return this.buffer;
    }

    public void allocateInt(long capacity) {
        allocate(capacity * DataType.INT.getBytes());
    }

    public void storeInt(long offset, int[] data) {
        getBuffer().store(TARGET, offset, data);
    }

    public void storeInt(long offset, IntBuffer data) {
        getBuffer().store(TARGET, offset, data);
    }

    @Override
    public void allocate(long capacity) {
        getBuffer().allocate(TARGET, capacity, this.usage.get());
    }

    @Override
    public void store(long offset, ByteBuffer buffer) {
        getBuffer().store(TARGET, offset, buffer);
    }

    @Override
    public ByteBuffer map(VboAccess access) {
        return getBuffer().map(TARGET, access.get());
    }

    @Override
    public void unmap() {
        getBuffer().unmap(TARGET);
    }

    @Override
    public long getCapacity() {
        return getBuffer().getCapacity();
    }

    @Override
    public void bind() {
        getBuffer().bind(TARGET);
    }

    @Override
    public void unbind() {
        getBuffer().unbind(TARGET);
    }

    @Override
    public void delete() {
        getBuffer().delete();
    }
}
