package org.saar.lwjgl.opengl.objects.buffers;

import org.lwjgl.opengl.GL15;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.objects.exceptions.GLBufferOverflowException;
import org.saar.lwjgl.opengl.utils.GlConfigs;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public final class BufferObject implements IBufferObject {

    public static final BufferObject NULL = new BufferObject(0);

    private final int id;

    private long capacity = -1;
    private boolean deleted = false;

    private BufferObject(int id) {
        this.id = id;
    }

    public static BufferObject create() {
        final int id = GL15.glGenBuffers();
        return new BufferObject(id);
    }

    private void ensureCapacity(long offset, long size) {
        if (offset + size > getCapacity()) {
            throw new GLBufferOverflowException("Buffer capacity too small, capacity: "
                    + getCapacity() + ", offset: " + offset + ", input size: " + size);
        }
    }

    private void beforeStore(BufferTarget target, long offset, int size, DataType dataType) {
        this.bind(target);
        final int bytes = dataType.getBytes();
        ensureCapacity(offset, (long) size * bytes);
    }

    @Override
    public void allocate(BufferTarget target, long capacity, BufferUsage usage) {
        bind(target);
        GL15.glBufferData(target.get(), capacity, usage.get());
        this.capacity = capacity;
    }

    @Override
    public void store(BufferTarget target, long offset, int[] buffer) {
        beforeStore(target, offset, buffer.length, DataType.INT);
        GL15.glBufferSubData(target.get(), offset, buffer);
    }

    @Override
    public void store(BufferTarget target, long offset, float[] buffer) {
        beforeStore(target, offset, buffer.length, DataType.FLOAT);
        GL15.glBufferSubData(target.get(), offset, buffer);
    }

    @Override
    public void store(BufferTarget target, long offset, ByteBuffer buffer) {
        beforeStore(target, offset, buffer.limit() - buffer.position(), DataType.BYTE);
        GL15.glBufferSubData(target.get(), offset, buffer);
    }

    @Override
    public void store(BufferTarget target, long offset, IntBuffer buffer) {
        beforeStore(target, offset, buffer.limit() - buffer.position(), DataType.INT);
        GL15.glBufferSubData(target.get(), offset, buffer);
    }

    @Override
    public void store(BufferTarget target, long offset, FloatBuffer buffer) {
        beforeStore(target, offset, buffer.limit() - buffer.position(), DataType.FLOAT);
        GL15.glBufferSubData(target.get(), offset, buffer);
    }

    @Override
    public ByteBuffer map(BufferTarget target, BufferAccess access) {
        bind(target);
        return GL15.glMapBuffer(target.get(), access.get());
    }

    @Override
    public void unmap(BufferTarget target) {
        bind(target);
        GL15.glUnmapBuffer(target.get());
    }

    @Override
    public void bind(BufferTarget target) {
        if (!GlConfigs.CACHE_STATE || !BoundBuffer.isBound(target, this.id)) {
            GL15.glBindBuffer(target.get(), this.id);
            BoundBuffer.set(target, this.id);
        }
    }

    @Override
    public void unbind(BufferTarget target) {
        BufferObject.NULL.bind(target);
    }

    @Override
    public void delete() {
        if (!GlConfigs.CACHE_STATE || !this.deleted) {
            GL15.glDeleteBuffers(this.id);
            this.deleted = true;
        }
    }

    @Override
    public long getCapacity() {
        return this.capacity;
    }
}
