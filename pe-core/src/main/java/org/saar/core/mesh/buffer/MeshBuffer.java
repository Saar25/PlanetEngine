package org.saar.core.mesh.buffer;

import org.saar.lwjgl.opengl.objects.vaos.WritableVao;
import org.saar.lwjgl.opengl.objects.vbos.IVbo;
import org.saar.lwjgl.util.DataWriter;
import org.saar.lwjgl.util.buffer.LwjglBuffer;
import org.saar.lwjgl.util.buffer.LwjglByteBuffer;

public abstract class MeshBuffer {

    protected final IVbo vbo;

    private LwjglBuffer buffer = null;

    public MeshBuffer(IVbo vbo) {
        this.vbo = vbo;
    }

    public abstract void loadInVao(WritableVao vao);

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

    public void update(int offset, int bytes) {
        this.buffer.position(offset).limit(offset + bytes);
        this.vbo.store(offset, this.buffer.asByteBuffer());
    }

    public DataWriter getWriter() {
        return this.buffer.getWriter();
    }
}