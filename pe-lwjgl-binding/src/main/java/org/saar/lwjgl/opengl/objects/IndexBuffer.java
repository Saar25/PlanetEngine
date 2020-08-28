package org.saar.lwjgl.opengl.objects;

import org.saar.lwjgl.opengl.constants.VboAccess;
import org.saar.lwjgl.opengl.constants.VboTarget;
import org.saar.lwjgl.opengl.constants.VboUsage;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class IndexBuffer implements IVbo, WriteableVbo {

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

    public void allocateByte(long size) {
        getVbo().allocateByte(size);
    }

    public void storeData(long pointer, int[] data) {
        getVbo().storeData(pointer, data);
    }

    public void storeData(long pointer, ByteBuffer data) {
        getVbo().storeData(pointer, data);
    }

    public void storeData(long pointer, IntBuffer data) {
        getVbo().storeData(pointer, data);
    }

    @Override
    public void allocateFloat(long size) {
        throw new UnsupportedOperationException("Cannot allocate float to index buffer");
    }

    @Override
    public void storeData(long offset, float[] data) {
        throw new UnsupportedOperationException("Cannot allocate float to index buffer");
    }

    @Override
    public void storeData(long offset, FloatBuffer data) {
        throw new UnsupportedOperationException("Cannot allocate float to index buffer");
    }

    public ByteBuffer map(VboAccess access) {
        return getVbo().map(access);
    }

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
