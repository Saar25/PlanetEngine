package org.saar.core.mesh.build;

import org.saar.lwjgl.opengl.objects.buffers.BufferObjectWrapper;
import org.saar.lwjgl.opengl.objects.vaos.WriteableVao;
import org.saar.lwjgl.util.buffer.BufferWriter;

public abstract class MeshBuffer {

    private final BufferObjectWrapper wrapper;

    public MeshBuffer(BufferObjectWrapper wrapper) {
        this.wrapper = wrapper;
    }

    public abstract void loadInVao(WriteableVao vao);

    protected void allocate(int capacity) {
        getWrapper().allocate(capacity);
    }

    public void store(long offset) {
        getWrapper().store(offset);
    }

    public BufferWriter getWriter() {
        return getWrapper().getWriter();
    }

    public BufferObjectWrapper getWrapper() {
        return this.wrapper;
    }
}
