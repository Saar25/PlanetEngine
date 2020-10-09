package org.saar.lwjgl.opengl.objects.buffers;

import org.lwjgl.opengl.GL15;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.VboAccess;
import org.saar.lwjgl.opengl.constants.VboTarget;
import org.saar.lwjgl.opengl.constants.VboUsage;
import org.saar.lwjgl.opengl.objects.exceptions.GlBufferOverflowException;
import org.saar.lwjgl.opengl.utils.GlConfigs;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public final class BufferObject {

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

    public void allocate(VboTarget target, long capacity, VboUsage usage) {
        bind(target);
        GL15.glBufferData(target.get(), capacity, usage.get());
        this.capacity = capacity;
    }

    private void beforeStore(VboTarget target, long offset, int limit, DataType dataType) {
        this.bind(target);
        final int bytes = dataType.getBytes();
        ensureCapacity(offset, limit * bytes);
    }

    public void store(VboTarget target, long offset, int[] buffer) {
        beforeStore(target, offset, buffer.length, DataType.INT);
        GL15.glBufferSubData(target.get(), offset, buffer);
    }

    public void store(VboTarget target, long offset, float[] buffer) {
        beforeStore(target, offset, buffer.length, DataType.FLOAT);
        GL15.glBufferSubData(target.get(), offset, buffer);
    }

    public void store(VboTarget target, long offset, ByteBuffer buffer) {
        beforeStore(target, offset, buffer.limit(), DataType.BYTE);
        GL15.glBufferSubData(target.get(), offset, buffer);
    }

    public void store(VboTarget target, long offset, IntBuffer buffer) {
        beforeStore(target, offset, buffer.limit(), DataType.INT);
        GL15.glBufferSubData(target.get(), offset, buffer);
    }

    public void store(VboTarget target, long offset, FloatBuffer buffer) {
        beforeStore(target, offset, buffer.limit(), DataType.FLOAT);
        GL15.glBufferSubData(target.get(), offset, buffer);
    }

    private void ensureCapacity(long offset, long size) {
        if (offset + size > getCapacity()) {
            throw new GlBufferOverflowException("Buffer capacity too small, capacity: "
                    + getCapacity() + ", offset: " + offset + ", input size: " + size);
        }
    }

    public ByteBuffer map(VboTarget target, VboAccess access) {
        bind(target);
        return GL15.glMapBuffer(target.get(), access.get());
    }

    public void unmap(VboTarget target) {
        bind(target);
        GL15.glUnmapBuffer(target.get());
    }

    public void bind(VboTarget target) {
        if (!GlConfigs.CACHE_STATE || !BoundBuffer.isBound(target, this.id)) {
            GL15.glBindBuffer(target.get(), this.id);
            BoundBuffer.set(target, this.id);
        }
    }

    public void unbind(VboTarget target) {
        BufferObject.NULL.bind(target);
    }

    public void delete() {
        if (!GlConfigs.CACHE_STATE || !isDeleted()) {
            GL15.glDeleteBuffers(this.id);
            this.deleted = true;
        }
    }

    public long getCapacity() {
        return this.capacity;
    }

    public boolean isDeleted() {
        return this.deleted;
    }
}
