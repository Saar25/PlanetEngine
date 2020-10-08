package org.saar.core.model.loader;

import org.lwjgl.system.MemoryUtil;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.objects.vbos.IVbo;
import org.saar.lwjgl.opengl.utils.BufferWriter;
import org.saar.lwjgl.opengl.utils.MemoryUtils;

import java.nio.ByteBuffer;

public class MeshBuffer {

    private final IVbo vbo;
    private final Attribute[] attributes;
    private BufferWriter writer;
    private ByteBuffer buffer;

    public MeshBuffer(IVbo vbo, Attribute... attributes) {
        this.vbo = vbo;
        this.attributes = attributes;
    }

    public void allocateByCount(int count) {
        allocate(sumBytes() * count);
    }

    public void allocate(int bytes) {
        MemoryUtil.memFree(this.buffer);
        this.buffer = MemoryUtil.memAlloc(bytes);
        this.writer = new BufferWriter(this.buffer);
    }

    public int sumBytes() {
        return Attribute.sumBytes(getAttributes());
    }

    public int getBufferCount() {
        return this.buffer.limit() / sumBytes();
    }

    public int getBufferSize() {
        return this.buffer.limit();
    }

    public IVbo getVbo() {
        return this.vbo;
    }

    public Attribute[] getAttributes() {
        return this.attributes;
    }

    public ByteBuffer getBuffer() {
        return this.buffer;
    }

    public BufferWriter getWriter() {
        return this.writer;
    }
}
