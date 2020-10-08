package org.saar.core.model.loader;

import org.lwjgl.system.MemoryUtil;
import org.saar.lwjgl.opengl.objects.vaos.Vao;
import org.saar.lwjgl.opengl.objects.vbos.IVbo;
import org.saar.lwjgl.opengl.objects.vbos.Vbos;

import java.util.ArrayList;
import java.util.List;

public abstract class MeshBuffersBase implements ModelBuffers {

    protected final Vao vao = Vao.create();

    private final List<ModelBuffer> buffers = new ArrayList<>();

    public void addModelBuffer(ModelBuffer buffer) {
        this.buffers.add(buffer);
    }

    protected final void updateBuffers() {
        for (final ModelBuffer modelBuffer : this.buffers) {
            modelBuffer.getBuffer().flip();
            final IVbo vbo = modelBuffer.getVbo();
            this.vao.loadVbo(vbo, modelBuffer.getAttributes());
            Vbos.allocateAndStore(vbo, modelBuffer.getBuffer());
        }
    }

    protected final void deleteBuffers() {
        for (final ModelBuffer modelBuffer : this.buffers) {
            MemoryUtil.memFree(modelBuffer.getBuffer());
        }
    }

}
