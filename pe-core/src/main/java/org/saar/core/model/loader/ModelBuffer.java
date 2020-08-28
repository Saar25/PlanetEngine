package org.saar.core.model.loader;

import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.objects.WriteableVbo;
import org.saar.lwjgl.opengl.utils.BufferWriter;

import java.nio.ByteBuffer;

public class ModelBuffer {

    private final WriteableVbo vbo;
    private final ByteBuffer buffer;
    private final BufferWriter writer;
    private final Attribute[] attributes;

    public ModelBuffer(WriteableVbo vbo, ByteBuffer buffer, BufferWriter writer, Attribute... attributes) {
        this.vbo = vbo;
        this.buffer = buffer;
        this.writer = writer;
        this.attributes = attributes;
    }

    public WriteableVbo getVbo() {
        return this.vbo;
    }

    public ByteBuffer getBuffer() {
        return this.buffer;
    }

    public BufferWriter getWriter() {
        return this.writer;
    }

    public Attribute[] getAttributes() {
        return this.attributes;
    }
}
