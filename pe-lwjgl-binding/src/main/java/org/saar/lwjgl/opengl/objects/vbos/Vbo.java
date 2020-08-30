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
        this.bind0();
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
        this.bind0();
        final int bytes = DataType.INT.getBytes();
        ensureSize(offset, data.length * bytes);
        GL15.glBufferSubData(target, offset, data);
    }

    @Override
    public void storeData(long offset, float[] data) {
        this.bind0();
        final int bytes = DataType.FLOAT.getBytes();
        ensureSize(offset, data.length * bytes);
        GL15.glBufferSubData(target, offset, data);
    }

    @Override
    public void storeData(long offset, ByteBuffer data) {
        this.bind0();
        ensureSize(offset, data.limit());
        GL15.glBufferSubData(target, offset, data);
    }

    @Override
    public void storeData(long offset, IntBuffer data) {
        this.bind0();
        final int bytes = DataType.INT.getBytes();
        ensureSize(offset, data.limit() * bytes);
        GL15.glBufferSubData(target, offset, data);
    }

    @Override
    public void storeData(long offset, FloatBuffer data) {
        this.bind0();
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
        this.bind0();
        return GL15.glMapBuffer(target, access.get());
    }

    public void unmap() {
        this.bind0();
        GL15.glUnmapBuffer(target);
    }

    /*
     *  Vbo utilities
     */

    @Override
    public long getSize() {
        return this.size;
    }

    private void bind0() {
        GL15.glBindBuffer(target, this.id);
        Vbo.boundVbo = this.id;
    }

    @Override
    public void bind() {
        if (GlConfigs.CACHE_STATE || boundVbo != id) {
            this.bind0();
        }
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
