package org.saar.lwjgl.opengl.objects;

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

public class Vbo implements IVbo, WriteableVbo {

    public static final Vbo NULL_ARRAY = new Vbo(0, VboTarget.ARRAY_BUFFER, VboUsage.STATIC_DRAW);
    public static final Vbo NULL_INDEX = new Vbo(0, VboTarget.ELEMENT_ARRAY_BUFFER, VboUsage.STATIC_DRAW);

    private static int boundVbo = 0;

    private final int id;
    private final int target;
    private final int usage;

    private long size = 0;
    private boolean deleted = false;

    private Vbo(int id, VboTarget target, VboUsage usage) {
        this.id = id;
        this.target = target.get();
        this.usage = usage.get();
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
        bind();
        GL15.glBufferData(target, size, usage);
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

    /*
     *  Vbo store data
     */

    @Override
    public void storeData(long offset, int[] data) {
        bind();
        final int bytes = DataType.INT.getBytes();
        ensureSize(offset, data.length * bytes);
        GL15.glBufferSubData(target, offset, data);
    }

    @Override
    public void storeData(long offset, float[] data) {
        bind();
        final int bytes = DataType.FLOAT.getBytes();
        ensureSize(offset, data.length * bytes);
        GL15.glBufferSubData(target, offset, data);
    }

    @Override
    public void storeData(long offset, ByteBuffer data) {
        bind();
        ensureSize(offset, data.limit());
        GL15.glBufferSubData(target, offset, data);
    }

    @Override
    public void storeData(long offset, IntBuffer data) {
        bind();
        final int bytes = DataType.INT.getBytes();
        ensureSize(offset, data.limit() * bytes);
        GL15.glBufferSubData(target, offset, data);
    }

    @Override
    public void storeData(long offset, FloatBuffer data) {
        bind();
        final int bytes = DataType.FLOAT.getBytes();
        ensureSize(offset, data.limit() * bytes);
        GL15.glBufferSubData(target, offset, data);
    }

    private void ensureSize(long offset, long size) {
        if (offset + size > getSize()) {
            throw new VboTooSmallException("Vbo storage too small, vbo size: "
                    + getSize() + ", offset: " + offset + ", input size: " + size);
        }
    }

    /*
     *  Vbo mapping
     */

    public ByteBuffer map(VboAccess access) {
        bind();
        return GL15.glMapBuffer(target, access.get());
    }

    public void unmap() {
        bind();
        GL15.glUnmapBuffer(target);
    }

    /*
     *  Vbo utilities
     */

    @Override
    public long getSize() {
        return this.size;
    }

    @Override
    public void bind() {
        if (GlConfigs.CACHE_STATE || boundVbo != id) {
            GL15.glBindBuffer(target, id);
            boundVbo = id;
        }
    }

    @Override
    public void bindToVao(Vao vao) {
        vao.bind();
        GL15.glBindBuffer(target, id);
        boundVbo = id;
    }

    @Override
    public void unbind() {
        if (GlConfigs.CACHE_STATE || boundVbo != 0) {
            GL15.glBindBuffer(target, 0);
            boundVbo = 0;
        }
    }

    @Override
    public void delete() {
        if (GlConfigs.CACHE_STATE || !deleted) {
            GL15.glDeleteBuffers(id);
            deleted = true;
        }
    }

}
