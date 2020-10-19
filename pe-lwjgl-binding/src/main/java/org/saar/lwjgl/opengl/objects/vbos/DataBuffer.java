package org.saar.lwjgl.opengl.objects.vbos;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class DataBuffer implements IVbo {

    public static final DataBuffer NULL = new DataBuffer(Vbo.NULL_ARRAY);

    private final Vbo vbo;

    private DataBuffer(Vbo vbo) {
        this.vbo = vbo;
    }

    public DataBuffer(VboUsage usage) {
        this.vbo = Vbo.create(VboTarget.ARRAY_BUFFER, usage);
    }

    private Vbo getVbo() {
        return this.vbo;
    }

    public void allocateFloat(long size) {
        getVbo().allocateFloat(size);
    }

    public void allocateInt(long size) {
        getVbo().allocateInt(size);
    }

    public void storeData(long offset, int[] data) {
        getVbo().storeData(offset, data);
    }

    public void storeData(long offset, float[] data) {
        getVbo().storeData(offset, data);
    }

    public void storeData(long offset, IntBuffer data) {
        getVbo().storeData(offset, data);
    }

    public void storeData(long offset, FloatBuffer data) {
        getVbo().storeData(offset, data);
    }

    @Override
    public void allocateByte(long size) {
        getVbo().allocateByte(size);
    }

    @Override
    public void storeData(long offset, ByteBuffer buffer) {
        getVbo().storeData(offset, buffer);
    }

    @Override
    public ByteBuffer map(VboAccess access) {
        return getVbo().map(access);
    }

    @Override
    public void unmap() {
        getVbo().unmap();
    }

    @Override
    public long getSize() {
        return getVbo().getSize();
    }

    @Override
    public void bind() {
        getVbo().bind();
    }

    @Override
    public void unbind() {
        getVbo().unbind();
    }

    @Override
    public void delete() {
        getVbo().delete();
    }
}
