package org.saar.core.model.mesh;

import org.saar.lwjgl.opengl.objects.vaos.WriteableVao;
import org.saar.lwjgl.opengl.objects.vbos.VboWrapper;
import org.saar.lwjgl.opengl.utils.BufferWriter;

public abstract class MeshBuffer {

    private final VboWrapper wrapper;

    public MeshBuffer(VboWrapper wrapper) {
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

    public VboWrapper getWrapper() {
        return this.wrapper;
    }
}
