package org.saar.core.model.loader;

import org.saar.core.model.Vertex;
import org.saar.core.node.Node;
import org.saar.lwjgl.opengl.objects.*;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractModelBuffers<N extends Node, V extends Vertex> implements ModelBuffers, ModelWriter<N, V> {

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
            final WriteableVbo vbo = modelBuffer.getVbo();
            Vbos.allocateAndStore(vbo, buffer);
        }
    }

    protected final void writeVertices(V[] vertices) {
        for (V vertex : vertices) {
            writeVertex(vertex);
        }
    }

    protected final void writeInstances(N[] instances) {
        for (N instance : instances) {
            writeInstance(instance);
        }
    }

    protected final void writeIndices(int[] indices) {
        for (int index : indices) {
            writeIndex(index);
        }
    }

}
