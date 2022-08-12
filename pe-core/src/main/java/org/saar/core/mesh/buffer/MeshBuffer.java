package org.saar.core.mesh.buffer;

import org.saar.lwjgl.opengl.vao.WritableVao;
import org.saar.lwjgl.opengl.vbo.IVbo;
import org.saar.lwjgl.util.DataReader;
import org.saar.lwjgl.util.DataWriter;
import org.saar.lwjgl.util.buffer.LwjglBuffer;
import org.saar.lwjgl.util.buffer.LwjglByteBuffer;

public abstract class MeshBuffer {

    protected final IVbo vbo;

    protected LwjglBuffer buffer = null;

    public MeshBuffer(IVbo vbo) {
        this.vbo = vbo;
    }

    public abstract void loadInVao(WritableVao vao);

    public void delete() {
        this.buffer.close();
    }

    protected void allocate(int capacity) {
        if (this.buffer != null) {
            this.buffer.close();
        }
        this.buffer = LwjglByteBuffer.allocate(capacity);
    }

    public void store(long offset) {
        this.vbo.allocate(this.buffer.flip().limit());
        this.vbo.store(offset, this.buffer.asByteBuffer());
    }

    public DataReader getReader() {
        return this.buffer.getReader();
    }

    public DataWriter getWriter() {
        return this.buffer.getWriter();
    }
}
