package org.saar.lwjgl.opengl.objects.vbos;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.buffers.BufferObject;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Vbo implements WriteableVbo {

    public static final Vbo NULL_ARRAY = new Vbo(BufferObject.NULL,
            VboTarget.ARRAY_BUFFER, VboUsage.STATIC_DRAW);

    public static final Vbo NULL_INDEX = new Vbo(BufferObject.NULL,
            VboTarget.ELEMENT_ARRAY_BUFFER, VboUsage.STATIC_DRAW);

    private final BufferObject buffer;
    private final VboTarget target;
    private final VboUsage usage;

    private Vbo(BufferObject buffer, VboTarget target, VboUsage usage) {
        this.buffer = buffer;
        this.target = target;
        this.usage = usage;
    }

    public static Vbo create(VboTarget target, VboUsage usage) {
        final BufferObject buffer = BufferObject.create();
        return new Vbo(buffer, target, usage);
    }

    @Override
    public void allocateByte(long size) {
        this.buffer.allocate(this.target.get(), size, this.usage.get());
    }

    @Override
    public void allocateInt(long size) {
        allocateByte(size * DataType.INT.getBytes());
    }

    @Override
    public void allocateFloat(long size) {
        allocateByte(size * DataType.FLOAT.getBytes());
    }

    @Override
    public void storeData(long offset, int[] data) {
        this.buffer.store(this.target.get(), offset, data);
    }

    @Override
    public void storeData(long offset, float[] data) {
        this.buffer.store(this.target.get(), offset, data);
    }

    @Override
    public void storeData(long offset, ByteBuffer buffer) {
        this.buffer.store(this.target.get(), offset, buffer);
    }

    @Override
    public void storeData(long offset, IntBuffer data) {
        this.buffer.store(this.target.get(), offset, data);
    }

    @Override
    public void storeData(long offset, FloatBuffer data) {
        this.buffer.store(this.target.get(), offset, data);
    }

    @Override
    public ByteBuffer map(VboAccess access) {
        return this.buffer.map(this.target.get(), access.get());
    }

    @Override
    public void unmap() {
        this.buffer.unmap(this.target.get());
    }

    @Override
    public long getSize() {
        return this.buffer.getCapacity();
    }

    @Override
    public void bind() {
        this.buffer.bind(this.target.get());
    }

    @Override
    public void unbind() {
        this.buffer.unbind(this.target.get());
    }

    @Override
    public void delete() {
        this.buffer.delete();
    }

}
