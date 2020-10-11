package org.saar.core.model.loader;

import org.lwjgl.system.MemoryUtil;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.objects.vaos.Vao;
import org.saar.lwjgl.opengl.objects.vbos.DataBuffer;
import org.saar.lwjgl.opengl.objects.vbos.IVbo;
import org.saar.lwjgl.opengl.objects.vbos.IndexBuffer;
import org.saar.lwjgl.opengl.objects.vbos.Vbos;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractModelBuffers implements ModelBuffers {

    protected final Vao vao = Vao.create();
    private final List<ModelBuffer> buffers = new ArrayList<>();

    protected final ModelBuffer loadDataBuffer(DataBuffer vbo, int count, Attribute... attributes) {
        final int bytes = Attribute.sumBytes(attributes) * count;
        final ModelBuffer modelBuffer = ModelBuffer.allocate(vbo, bytes);
        this.vao.loadVbo(vbo, attributes);
        this.buffers.add(modelBuffer);
        return modelBuffer;
    }

    protected final ModelBuffer loadIndexBuffer(IndexBuffer vbo, int indices) {
        final ModelBuffer modelBuffer = ModelBuffer.allocate(vbo, indices * 4);
        this.buffers.add(modelBuffer);
        this.vao.loadVbo(vbo);
        return modelBuffer;
    }

    protected final void updateBuffers() {
        for (final ModelBuffer modelBuffer : this.buffers) {
            final ByteBuffer buffer = modelBuffer.getBuffer();
            final IVbo vbo = modelBuffer.getVbo();
            buffer.flip();
            Vbos.allocateAndStore(vbo, buffer);
        }
    }

    protected final void deleteBuffers() {
        for (final ModelBuffer modelBuffer : this.buffers) {
            MemoryUtil.memFree(modelBuffer.getBuffer());
        }
    }

}
