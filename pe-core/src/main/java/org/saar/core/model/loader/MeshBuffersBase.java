package org.saar.core.model.loader;

import org.lwjgl.system.MemoryUtil;
import org.saar.lwjgl.opengl.objects.vaos.Vao;
import org.saar.lwjgl.opengl.objects.vbos.IVbo;
import org.saar.lwjgl.opengl.objects.vbos.Vbos;

import java.util.ArrayList;
import java.util.List;

public abstract class MeshBuffersBase implements MeshBuffers {

    protected final Vao vao = Vao.create();

    private final List<MeshBuffer> buffers = new ArrayList<>();

    public void addMeshBuffer(MeshBuffer buffer) {
        this.buffers.add(buffer);
    }

    protected final void updateBuffers() {
        for (final MeshBuffer meshBuffer : this.buffers) {
            meshBuffer.getBuffer().flip();
            final IVbo vbo = meshBuffer.getVbo();
            this.vao.loadVbo(vbo, meshBuffer.getAttributes());
            Vbos.allocateAndStore(vbo, meshBuffer.getBuffer());
        }
    }

    protected final void deleteBuffers() {
        for (final MeshBuffer meshBuffer : this.buffers) {
            MemoryUtil.memFree(meshBuffer.getBuffer());
        }
    }

}
