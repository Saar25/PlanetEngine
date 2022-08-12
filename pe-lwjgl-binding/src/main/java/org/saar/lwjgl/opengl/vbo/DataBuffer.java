package org.saar.lwjgl.opengl.vbo;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.buffer.BufferObject;
import org.saar.lwjgl.opengl.buffer.BufferTarget;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class DataBuffer implements IVbo {

    private static final BufferTarget TARGET = BufferTarget.ARRAY;

    public static final DataBuffer NULL = new DataBuffer(BufferObject.NULL, VboUsage.STATIC_DRAW);

    private final BufferObject buffer;
    private final VboUsage usage;

    private DataBuffer(BufferObject buffer, VboUsage usage) {
        this.buffer = buffer;
        this.usage = usage;
    }

    public DataBuffer(VboUsage usage) {
        this(BufferObject.create(), usage);
    }

    private BufferObject getBuffer() {
        return this.buffer;
    }

    public void allocateInt(long capacity) {
        allocate(capacity * DataType.INT.getBytes());
    }

    public void allocateFloat(long capacity) {
        allocate(capacity * DataType.FLOAT.getBytes());
    }

    public void storeInt(long offset, int[] data) {
        getBuffer().store(TARGET, offset, data);
    }

    public void storeInt(long offset, IntBuffer data) {
        getBuffer().store(TARGET, offset, data);
    }

    public void storeFloat(long offset, float[] data) {
        getBuffer().store(TARGET, offset, data);
    }

    public void storeFloat(long offset, FloatBuffer data) {
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
