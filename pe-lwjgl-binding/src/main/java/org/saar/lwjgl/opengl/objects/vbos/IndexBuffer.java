package org.saar.lwjgl.opengl.objects.vbos;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class IndexBuffer implements IVbo {

    public static final IndexBuffer NULL = new IndexBuffer(Vbo.NULL_INDEX);

    private final Vbo vbo;

    private IndexBuffer(Vbo vbo) {
        this.vbo = vbo;
    }

    public IndexBuffer(VboUsage usage) {
        this.vbo = Vbo.create(VboTarget.ELEMENT_ARRAY_BUFFER, usage);
    }

    private Vbo getVbo() {
        return this.vbo;
    }

    public void allocateInt(long size) {
        getVbo().allocateInt(size);
    }

    public void storeInt(long offset, int[] data) {
        getVbo().storeInt(offset, data);
    }

    public void storeInt(long offset, IntBuffer data) {
        getVbo().storeInt(offset, data);
    }

    @Override
    public void allocate(long size) {
        getVbo().allocate(size);
    }

    @Override
    public void store(long offset, ByteBuffer buffer) {
        getVbo().store(offset, buffer);
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
