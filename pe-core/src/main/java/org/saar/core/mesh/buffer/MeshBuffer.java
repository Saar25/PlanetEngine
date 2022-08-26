package org.saar.core.mesh.buffer;

import org.saar.lwjgl.opengl.attribute.IAttribute;
import org.saar.lwjgl.opengl.vao.WritableVao;
import org.saar.lwjgl.opengl.vbo.IVbo;
import org.saar.lwjgl.util.buffer.LwjglBuffer;

import java.util.List;

public class MeshBuffer {

    private final IVbo vbo;
    private final LwjglBuffer buffer;
    private final List<IAttribute> attributes;

    public MeshBuffer(IVbo vbo, LwjglBuffer buffer, List<IAttribute> attributes) {
        this.vbo = vbo;
        this.buffer = buffer;
        this.attributes = attributes;
    }

    public void store(long offset) {
        this.vbo.allocate(this.buffer.flip().limit());
        this.vbo.store(offset, this.buffer.asByteBuffer());
    }

    public void loadInVao(WritableVao vao) {
        vao.loadVbo(this.vbo, attributesArray());
    }

    private IAttribute[] attributesArray() {
        return this.attributes.toArray(new IAttribute[0]);
    }

    public void delete() {
        this.buffer.close();
    }
}
