package org.saar.lwjgl.opengl.objects.vbos;

import org.lwjgl.opengl.GL15;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.VboAccess;
import org.saar.lwjgl.opengl.constants.VboTarget;
import org.saar.lwjgl.opengl.constants.VboUsage;
import org.saar.lwjgl.opengl.objects.exceptions.VboTooSmallException;
import org.saar.lwjgl.opengl.utils.GlConfigs;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Vbo implements WriteableVbo {

    public static final Vbo NULL_ARRAY = new Vbo(0, VboTarget.ARRAY_BUFFER, VboUsage.STATIC_DRAW);
    public static final Vbo NULL_INDEX = new Vbo(0, VboTarget.ELEMENT_ARRAY_BUFFER, VboUsage.STATIC_DRAW);

    private final int id;
    private final VboTarget target;
    private final VboUsage usage;

    private long size = 0;
    private boolean deleted = false;

    private Vbo(int id, VboTarget target, VboUsage usage) {
        this.id = id;
        this.target = target;
        this.usage = usage;
    }

    public static Vbo create(VboTarget target, VboUsage usage) {
        final int id = GL15.glGenBuffers();
        return new Vbo(id, target, usage);
    }

    /*
     *  Vbo allocate data
     */

    @Override
    public void allocateByte(long size) {
        this.bind();
        GL15.glBufferData(this.target.get(), size, this.usage.get());
        this.size = size;
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
        this.bind();
        final int bytes = DataType.INT.getBytes();
        ensureSize(offset, data.length * bytes);
        GL15.glBufferSubData(this.target.get(), offset, data);
    }

    @Override
    public void storeData(long offset, float[] data) {
        this.bind();
        final int bytes = DataType.FLOAT.getBytes();
        ensureSize(offset, data.length * bytes);
        GL15.glBufferSubData(this.target.get(), offset, data);
    }

    @Override
    public void storeData(long offset, ByteBuffer buffer) {
        this.bind();
        ensureSize(offset, buffer.limit());
        GL15.glBufferSubData(this.target.get(), offset, buffer);
    }

    @Override
    public void storeData(long offset, IntBuffer data) {
        this.bind();
        final int bytes = DataType.INT.getBytes();
        ensureSize(offset, data.limit() * bytes);
        GL15.glBufferSubData(this.target.get(), offset, data);
    }

    @Override
    public void storeData(long offset, FloatBuffer data) {
        this.bind();
        final int bytes = DataType.FLOAT.getBytes();
        ensureSize(offset, data.limit() * bytes);
        GL15.glBufferSubData(this.target.get(), offset, data);
    }

    private void ensureSize(long offset, long size) {
        if (offset + size > getSize()) {
            throw new VboTooSmallException("Vbo storage too small, vbo size: "
                    + getSize() + ", offset: " + offset + ", input size: " + size);
        }
    }

    @Override
    public ByteBuffer map(VboAccess access) {
        this.bind();
        return GL15.glMapBuffer(this.target.get(), access.get());
    }

    @Override
    public void unmap() {
        this.bind();
        GL15.glUnmapBuffer(this.target.get());
    }

    @Override
    public long getSize() {
        return this.size;
    }

    @Override
    public void bind() {
        if (!GlConfigs.CACHE_STATE || !BoundVbo.isBound(this.target, this.id)) {
            GL15.glBindBuffer(this.target.get(), this.id);
            BoundVbo.set(this.target, this.id);
        }
    }

    @Override
    public void unbind() {
        if (!GlConfigs.CACHE_STATE || !BoundVbo.isBound(this.target, 0)) {
            GL15.glBindBuffer(this.target.get(), 0);
            BoundVbo.set(this.target, 0);
        }
    }

    @Override
    public void delete() {
        if (!GlConfigs.CACHE_STATE || !this.deleted) {
            GL15.glDeleteBuffers(this.id);
            this.deleted = true;
        }
    }

}
